package com.wondoo.memberservice.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wondoo.memberservice.auth.client.github.data.response.GithubUserInfoResponse;
import com.wondoo.memberservice.follow.repository.FollowRepository;
import com.wondoo.memberservice.global.exception.ServerErrorCode;
import com.wondoo.memberservice.global.exception.ServerException;
import com.wondoo.memberservice.global.utils.KafkaProducer;
import com.wondoo.memberservice.infra.OpenAiClient;
import com.wondoo.memberservice.member.data.message.InfoMessage;
import com.wondoo.memberservice.member.data.query.MemberSearchQuery;
import com.wondoo.memberservice.member.data.request.MemberRankingInitRequest;
import com.wondoo.memberservice.member.data.request.MemberUpdateRequest;
import com.wondoo.memberservice.member.data.request.TokenUpdateRequest;
import com.wondoo.memberservice.member.data.response.*;
import com.wondoo.memberservice.member.domain.Level;
import com.wondoo.memberservice.member.domain.Member;
import com.wondoo.memberservice.member.domain.Statistic;
import com.wondoo.memberservice.member.exception.MemberErrorCode;
import com.wondoo.memberservice.member.exception.MemberException;
import com.wondoo.memberservice.member.repository.MemberRepository;
import com.wondoo.memberservice.member.repository.StatisticRepository;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.jetbrains.annotations.Nullable;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService implements MemberSaveService, MemberLoadService {

    private final MemberRepository memberRepository;
    private final StatisticRepository statisticRepository;
    private final FollowRepository followRepository;
    private final OpenAiClient openAiClient;
    private final RedisTemplate<String, String> redisTemplate;
    private final RestTemplate restTemplate;
    private final KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper;

    /**
     * Member 조회
     *
     * @param memberId Member PK
     * @return nickname, name, email 반환 / 추후 이미지 추가
     */
    @Override
    public MemberDetailResponse memberDetailLoad(Long memberId, Long checkId) {

        Member member = findById(memberId);
        Statistic statistic = statisticRepository.findById(
                member.getStatistic()
                        .getId()).get();

        return MemberDetailResponse.builder()
                .memberId(member.getId())
                .imageId(member.getImageId())
                .coverImageId(member.getCoverImageId())
                .nickname(member.getNickname())
                .name(member.getName())
                .email(member.getEmail())
                .phone(member.getPhone())
                .gender(member.getGender())
                .isFollow(followRepository.findByFollow(memberId, checkId).isPresent())
                .createTime(member.getCreateTime())
                .followingCount(statistic.getFollowingCount())
                .followerCount(statistic.getFollowerCount())
                .level(member.getLevel())
                .build();
    }

    /**
     * nickname 이 keyword 를 포함하면 검색됨
     * 총 9개 반환 (member_id ASC 9개)
     *
     * @param keyword 검색 조건
     * @return 검색 결과 리스트 반환
     */
    @Override
    public MemberSearchResponse memberSearchLoad(String keyword) {

        List<MemberSearchQuery> memberSearchQueries = memberRepository.memberSearch(keyword);
        return MemberSearchResponse.builder()
                .memberSearchQueries(memberSearchQueries)
                .build();
    }

    /**
     * Member Ranking 정보 반환
     *
     * @return TOP 3 반환
     */
    @Override
    public MemberRankingResponse memberRankingLoad() {

        Boolean memberRanking = redisTemplate.hasKey("member_ranking");
        log.info("-------EXIST?-----------");
        if (Boolean.FALSE.equals(memberRanking)) {
            return MemberRankingResponse.builder()
                    .memberRanking(null)
                    .build();
        }
        log.info("---------YES--------");
        Set<ZSetOperations.TypedTuple<String>> rankingSet = redisTemplate.opsForZSet().reverseRangeWithScores("member_ranking", 0, 2);
        log.info("-----RANKING---------");
        log.info("Ranking: [{}]", rankingSet);
        if (rankingSet != null) {
            List<MemberRankingInfoResponse> rankingList = rankingSet.stream()
                    .map(tuple -> {
                        String info = tuple.getValue();
                        Double score = tuple.getScore();
                        try {
                            log.info("-----PARSING-----");
                            MemberRankingInitRequest memberRankingInitRequest = objectMapper.readValue(info, MemberRankingInitRequest.class);
                            log.info("-----RESULT-----: [{}]", memberRankingInitRequest);
                            Member member = memberRepository.findById(memberRankingInitRequest.memberId()).get();
                            log.info("--------MEMBER------: [{}]", member);
                            return MemberRankingInfoResponse.builder()
                                    .memberId(member.getId())
                                    .nickname(member.getNickname())
                                    .imageId(member.getImageId())
                                    .level(member.getLevel())
                                    .score(score)
                                    .build();
                        } catch (JsonProcessingException e) {
                            throw new ServerException(ServerErrorCode.SERVER_ERROR_CODE);
                        }
                    })
                    .collect(Collectors.toList());
            return MemberRankingResponse.builder()
                    .memberRanking(rankingList)
                    .build();
        }


        return MemberRankingResponse.builder()
                .memberRanking(null)
                .build();
    }

    /**
     * Member 정보 수정
     * nickname, email, phone 필드는 유효성 검증 후 수정
     *
     * @param memberId            Member PK
     * @param memberUpdateRequest 수정하고자 하는 필드
     */
    @Transactional
    @Override
    public MemberUpdateResponse memberUpdate(Long memberId, MemberUpdateRequest memberUpdateRequest) {

        Member member = findById(memberId);

        member.updateMemberInfo(
                memberUpdateRequest.nickname(),
                memberUpdateRequest.name(),
                memberUpdateRequest.email(),
                memberUpdateRequest.phone(),
                memberUpdateRequest.gender()
        );

        try {
            kafkaProducer.sendInfoMessage(
                    objectMapper.writeValueAsString(
                            InfoMessage.builder()
                                    .memberId(member.getId())
                                    .imageId(member.getImageId())
                                    .level(String.valueOf(member.getLevel()))
                                    .nickname(member.getNickname())
                                    .build()
                    )
            );
        } catch (JsonProcessingException e) {
            throw new ServerException(ServerErrorCode.SERVER_ERROR_CODE);
        }

        return MemberUpdateResponse.builder()
                .nickname(member.getNickname())
                .name(member.getName())
                .email(member.getEmail())
                .phone(member.getPhone())
                .email(member.getEmail())
                .build();
    }

    /**
     * 프로필 이미지 등록 요청
     *
     * @param memberId 요청자 member_id
     * @param image    요청 이미지
     */
    @Transactional
    @Override
    public ImageResponse updateImage(Long memberId, MultipartFile image) throws IOException {
        Member member = findById(memberId);

        if (member.getImageId() != null) {

            imageDelete(member.getImageId());
        }

        log.info("-------Image Start------");
        ImageSaveResponse imageSaveResponse = getImageSaveResponse(image);
        log.info("-------Image End---------");
        member.updateImage(imageSaveResponse.fileSource());

        try {
            kafkaProducer.sendInfoMessage(
                    objectMapper.writeValueAsString(
                            InfoMessage.builder()
                                    .memberId(member.getId())
                                    .imageId(member.getImageId())
                                    .level(String.valueOf(member.getLevel()))
                                    .nickname(member.getNickname())
                                    .build()
                    )
            );
        } catch (JsonProcessingException e) {
            throw new ServerException(ServerErrorCode.SERVER_ERROR_CODE);
        }

        return ImageResponse.builder()
                .imageId(member.getImageId())
                .build();
    }

    /**
     * 커버 이미지 등록 요청
     *
     * @param memberId 요청자 member_id
     * @param image    요청 이미지
     */
    @Transactional
    @Override
    public ImageResponse updateCoverImage(Long memberId, MultipartFile image) throws IOException {
        Member member = findById(memberId);

        if (member.getCoverImageId() != null) {

            imageDelete(member.getCoverImageId());
        }

        ImageSaveResponse imageSaveResponse = getImageSaveResponse(image);

        member.updateCoverImage(imageSaveResponse.fileSource());

        return ImageResponse.builder()
                .imageId(member.getCoverImageId())
                .build();
    }


    /**
     * 주간 랭킹 Init
     */
    @Transactional
    @Override
    public void memberRankingInit() {

        Boolean memberRanking = redisTemplate.hasKey("member_ranking");
        if (Boolean.TRUE.equals(memberRanking)) {
            redisTemplate.opsForZSet().remove("member_ranking");
        }
        log.info("Remove Member Ranking");
        List<Member> members = memberRepository.findAll();
        if (!members.isEmpty()) {
            log.info("List<Member> : [{}]", members);
            members.forEach(member -> {
                try {
                    String memberInfo = objectMapper.writeValueAsString(MemberRankingInitRequest.builder()
                            .memberId(member.getId())
                            .build());

                    redisTemplate.opsForZSet().add("member_ranking", memberInfo, 0);
                } catch (JsonProcessingException e) {
                    throw new ServerException(ServerErrorCode.SERVER_ERROR_CODE);
                }
            });
        }
        log.info("Member Ranking Insert");
        redisTemplate.expire("member_ranking", 7, TimeUnit.DAYS);
    }

    /**
     * Member Level 상태 갱신
     */
    @Transactional
    @Override
    public void memberLevelReload() {

        List<Member> members = memberRepository.findAll();
        for (Member member : members) {
            @Min(0) Integer point = member.getPoint().getPoint();
            Level level = calculateLevel(point);

            if (!level.equals(member.getLevel())) {
                member.updateLevel(level);
                try {
                    kafkaProducer.sendInfoMessage(
                            objectMapper.writeValueAsString(
                                    InfoMessage.builder()
                                            .memberId(member.getId())
                                            .imageId(member.getImageId())
                                            .level(String.valueOf(member.getLevel()))
                                            .nickname(member.getNickname())
                                            .build()
                            )
                    );
                } catch (JsonProcessingException e) {
                    throw new ServerException(ServerErrorCode.SERVER_ERROR_CODE);
                }
            }
        }
    }

    private Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND)
                );
    }

    private Level calculateLevel(Integer point) {
        if (point < 100) {
            return Level.BEAN_1;
        } else if (point < 200) {
            return Level.BEAN_2;
        } else if (point < 300) {
            return Level.BEAN_3;
        } else if (point < 400) {
            return Level.BEAN_4;
        } else if (point < 1000) {
            return Level.BEAN_5;
        } else if (point < 1500) {
            return Level.GRINDER_1;
        } else if (point < 2000) {
            return Level.GRINDER_2;
        } else if (point < 2500) {
            return Level.GRINDER_3;
        } else if (point < 3000) {
            return Level.GRINDER_4;
        } else if (point < 5000) {
            return Level.GRINDER_5;
        } else if (point < 6000) {
            return Level.COFFEE_1;
        } else if (point < 7000) {
            return Level.COFFEE_2;
        } else if (point < 8000) {
            return Level.COFFEE_3;
        } else if (point < 9000) {
            return Level.COFFEE_4;
        } else if (point < 20000) {
            return Level.COFFEE_5;
        } else {
            return Level.WONDOO;
        }
    }

    @Transactional
    @Override
    public void updateOpenaiToken(Long memberId, TokenUpdateRequest request) {
        Member findMember = findById(memberId);

        String token = request.token();
        int statusCode = openAiClient.getStatusCodeByConnection(token);
        validateStatusCode(statusCode);

        findMember.updateOpenaiToken(token);
    }

    /**
     * Github Token 유효성 검사 후 저장
     *
     * @param memberId 요청자 member_id
     * @param request  요청자 Github Token
     */
    @Transactional
    @Override
    public void updateGithubToken(Long memberId, TokenUpdateRequest request) {
        Member findMember = findById(memberId);
        String token = request.token();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        int statusCode = getStatusCodeByConnection(entity);
        if (statusCode != 200) {
            throw new MemberException(MemberErrorCode.INVALID_GITHUB_TOKEN);
        }

        findMember.updateGithubToken(token);
    }

    private int getStatusCodeByConnection(HttpEntity<String> entity) {
        try {
            ResponseEntity<GithubUserInfoResponse> exchange = restTemplate.exchange(
                    "https://api.github.com/user",
                    HttpMethod.GET,
                    entity,
                    GithubUserInfoResponse.class
            );
            return exchange.getStatusCode().value();
        } catch (HttpClientErrorException e) {
            return HttpStatus.UNAUTHORIZED.value();
        }
    }

    private void validateStatusCode(int statusCode) {
        if (statusCode != 200) {
            throw new MemberException(MemberErrorCode.INVALID_OPENAI_TOKEN);
        }
    }

    private void imageDelete(String imageId) {
        MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("file_source", imageId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        restTemplate.exchange(
                "http://dev.wondoo.kr/storage-service/file",
                HttpMethod.DELETE,
                requestEntity,
                Void.class
        );
    }

//    @Nullable
    private ImageSaveResponse getImageSaveResponse(MultipartFile image) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> entity = getMultiValueMapHttpEntity(image, headers);
        log.info("------S3 Start------");
        ImageSaveResponse response = restTemplate.exchange(
                "https://dev.wondoo.kr/storage-service/file",
                HttpMethod.POST,
                entity,
                ImageSaveResponse.class
        ).getBody();
        log.info("-------S3 End-------");
        return ImageSaveResponse.builder()
                .fileSource(response.fileSource())
                .build();
    }

    private HttpEntity<MultiValueMap<String, Object>> getMultiValueMapHttpEntity(MultipartFile image, HttpHeaders headers) throws IOException {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        Resource resource = new ByteArrayResource(image.getBytes()) {
            @Override
            public String getFilename() {
                return image.getOriginalFilename();
            }
        };
        body.add("file_source", resource);

        return new HttpEntity<>(body, headers);
    }
}
