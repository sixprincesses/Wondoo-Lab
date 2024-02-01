package com.wondoo.memberservice.member.service;

import com.wondoo.memberservice.member.data.request.MemberUpdateRequest;
import com.wondoo.memberservice.member.data.response.BetweenServerResponse;
import com.wondoo.memberservice.member.data.response.ImageSaveResponse;
import com.wondoo.memberservice.member.data.response.MemberDetailResponse;
import com.wondoo.memberservice.member.domain.Member;
import com.wondoo.memberservice.member.domain.Statistic;
import com.wondoo.memberservice.member.exception.MemberErrorCode;
import com.wondoo.memberservice.member.exception.MemberException;
import com.wondoo.memberservice.member.repository.MemberRepository;
import com.wondoo.memberservice.member.repository.StatisticRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService implements MemberSaveService, MemberLoadService {

    private final MemberRepository memberRepository;
    private final StatisticRepository statisticRepository;
    private final RestTemplate restTemplate;

    /**
     * Member 조회
     *
     * @param memberId Member PK
     * @return nickname, name, email 반환 / 추후 이미지 추가
     */
    @Override
    public MemberDetailResponse memberDetailLoad(Long memberId) {

        Member member = findById(memberId);
        Statistic statistic = statisticRepository.findById(
                member.getStatistic()
                        .getId()).get();

        return MemberDetailResponse.builder()
                .nickname(member.getNickname())
                .name(member.getName())
                .email(member.getEmail())
                .followingCount(statistic.getFollowingCount())
                .followerCount(statistic.getFollowerCount())
                .build();
    }

    /**
     * 타 서버 요청자 검증용 social_id 반환 로직
     *
     * @param memberId Member PK
     * @return social_id 반환
     */
    @Override
    public BetweenServerResponse betweenServerLoad(Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND)
                );
        return BetweenServerResponse.builder()
                .social_id(member.getSocialId())
                .build();
    }

    /**
     * Member 정보 수정
     * nickname, email, phone 필드는 유효성 검증 후 수정
     *
     * @param memberId            Member PK
     * @param socialId            요청자와 접근하고자 하는 Member 동일성 검증
     * @param memberUpdateRequest 수정하고자 하는 필드
     */
    @Transactional
    @Override
    public void memberUpdate(Long memberId, Long socialId, MemberUpdateRequest memberUpdateRequest, MultipartFile imageId) throws IOException {

        Member member = findById(memberId);
        validRequester(socialId, member);
        String image = null;

        if (!imageId.isEmpty()){
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> entity = getMultiValueMapHttpEntity(imageId, headers);

            ResponseEntity<ImageSaveResponse> imageResponse = restTemplate.exchange(
                    "http://70.12.247.45:8080/storage-service/server/file",
                    HttpMethod.POST,
                    entity,
                    ImageSaveResponse.class
            );
            image = imageResponse.getBody().fileSource();
        }
        member.updateMemberInfo(
                memberUpdateRequest.nickname(),
                memberUpdateRequest.name(),
                memberUpdateRequest.email(),
                memberUpdateRequest.phone(),
                memberUpdateRequest.gender(),
                image
        );
    }

    private void validRequester(Long socialId, Member member) {
        if (!member.getSocialId().equals(socialId)) {
            throw new MemberException(MemberErrorCode.MEMBER_WRONG_ACCESS);
        }
    }

    private Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND)
                );
    }

    private static HttpEntity<MultiValueMap<String, Object>> getMultiValueMapHttpEntity(MultipartFile image, HttpHeaders headers) throws IOException {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        Resource resource = new ByteArrayResource(image.getBytes()){
            @Override
            public String getFilename() {
                return image.getOriginalFilename();
            }
        };
        body.add("file_source", resource);

        return new HttpEntity<>(body, headers);
    }
}
