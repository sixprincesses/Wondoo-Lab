package com.wondoo.memberservice.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wondoo.memberservice.auth.data.request.RefreshRelatedRequest;
import com.wondoo.memberservice.auth.data.response.TokenResponse;
import com.wondoo.memberservice.auth.utils.TokenProvider;
import com.wondoo.memberservice.auth.client.github.data.response.GithubUserInfoResponse;
import com.wondoo.memberservice.global.exception.ServerErrorCode;
import com.wondoo.memberservice.global.exception.ServerException;
import com.wondoo.memberservice.global.utils.KafkaProducer;
import com.wondoo.memberservice.member.data.message.InfoMessage;
import com.wondoo.memberservice.member.data.request.MemberRankingInitRequest;
import com.wondoo.memberservice.member.domain.Attendance;
import com.wondoo.memberservice.member.domain.Member;
import com.wondoo.memberservice.member.domain.Statistic;
import com.wondoo.memberservice.member.repository.AttendanceRepository;
import com.wondoo.memberservice.member.repository.MemberRepository;
import com.wondoo.memberservice.point.domain.Point;
import com.wondoo.memberservice.point.domain.PointReceipt;
import com.wondoo.memberservice.point.repository.PointReceiptRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AuthService {

    private final MemberRepository memberRepository;
    private final AttendanceRepository attendanceRepository;
    private final PointReceiptRepository pointReceiptRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final KafkaProducer kafkaProducer;
    private final TokenProvider tokenProvider;
    private final ObjectMapper objectMapper;

    /**
     * 회원가입 / 로그인
     *
     * @param githubUserInfoResponse Github 에 등록된 사용자 정보
     * @return JWT 정보 반환
     */
    @Transactional
    public ResponseEntity<TokenResponse> memberSave(GithubUserInfoResponse githubUserInfoResponse) {

        // 해당 social_id로 저장된 Member 정보가 있는지 확인
        Optional<Member> tmpMember = memberRepository.findBySocialId(githubUserInfoResponse.socialId());

        // 회원 가입 처리 후 반환
        if (tmpMember.isEmpty()) {
            TokenResponse tokenResponse = getTokenMarkerForSignup(githubUserInfoResponse);

            return ResponseEntity.status(HttpStatus.CREATED).body(tokenResponse);
        }

        Member member = tmpMember.get();
        // Github Nickname 을 변경했으면 반영
        if (isDifferentPreSocialNickname(githubUserInfoResponse, member)) {
            member.updateSocialNickname(githubUserInfoResponse.socialNickname());
        }

        attendanceCheck(member);
        TokenResponse tokenResponse = tokenProvider.jwtSave(member.getId(), member);

        return ResponseEntity.status(HttpStatus.OK).body(tokenResponse);
    }

    /**
     * 회원 로그아웃
     * refresh_token 검증 후 제거로 로그아웃 처리
     *
     * @param memberId              헤더에 담은 member_id
     * @param refreshRelatedRequest 클라이언트가 관리하는 refresh_token
     */
    @Transactional
    public void memberLogout(
            Long memberId,
            RefreshRelatedRequest refreshRelatedRequest
    ) {

        checkRefreshToken(memberId, refreshRelatedRequest);
    }

    /**
     * JWT Refresh 요청
     * refresh_token 검증 후 JWT 재발급
     *
     * @param memberId              헤더에 담은 social_id
     * @param refreshRelatedRequest 클라이언트가 관리하는 refresh_token
     * @return JWT 반환
     */
    @Transactional
    public TokenResponse memberRefresh(
            Long memberId,
            RefreshRelatedRequest refreshRelatedRequest
    ) {

        checkRefreshToken(memberId, refreshRelatedRequest);

        return tokenProvider.jwtSave(memberId, null);
    }

    private void checkRefreshToken(Long memberId, RefreshRelatedRequest refreshRelatedRequest) {

        tokenProvider.checkRefreshToken(memberId, refreshRelatedRequest);
    }

    private TokenResponse getTokenMarkerForSignup(GithubUserInfoResponse githubUserInfoResponse) {
        Member member = memberRepository.save(Member.builder()
                .socialId(githubUserInfoResponse.socialId())
                .socialNickname(githubUserInfoResponse.socialNickname())
                .statistic(Statistic
                        .builder().build())
                .point(Point
                        .builder().build())
                .build());
        attendanceCheck(member);

        try {
            kafkaProducer.sendInfoMessage(
                    objectMapper.writeValueAsString(
                            InfoMessage.builder()
                                    .memberId(member.getId())
                                    .level(String.valueOf(member.getLevel()))
                                    .nickname(member.getNickname())
                                    .imageId(member.getImageId())
                                    .build()
                    )
            );
        } catch (JsonProcessingException e){
            throw new ServerException(ServerErrorCode.SERVER_ERROR_CODE);
        }

        return tokenProvider.jwtSave(member.getId(), member);
    }

    private boolean isDifferentPreSocialNickname(GithubUserInfoResponse githubUserInfoResponse, Member member) {
        return !githubUserInfoResponse.socialNickname().equals(member.getSocialNickname());
    }

    private void attendanceCheck(Member member) {
        LocalDate today = LocalDate.now();
        Optional<Attendance> attendanceCheck = attendanceRepository.findByDateAndMember(today, member);
        if (attendanceCheck.isEmpty()) {
            attendanceRepository.save(
                    Attendance.builder()
                            .member(member)
                            .date(today)
                            .build()
            );
            // 포인트 부여
            member.getPoint()
                    .pointUpdate(1);
            // 영수증 발급
            pointReceiptRepository.save(
                    PointReceipt.builder()
                            .memberId(member.getId())
                            .type("출석 보상")
                            .point(1)
                            .time(System.currentTimeMillis())
                            .build()
            );
            // 랭킹 반영
            MemberRankingInitRequest memberRankingInitRequest = MemberRankingInitRequest.builder()
                    .memberId(member.getId())
                    .build();
            try {
                String memberRanking = objectMapper.writeValueAsString(memberRankingInitRequest);
                if (Boolean.TRUE.equals(redisTemplate.hasKey("member_ranking"))) {
                    redisTemplate.opsForZSet().incrementScore("member_ranking", memberRanking, 1L);
                }

            } catch (JsonProcessingException e){
                throw new ServerException(ServerErrorCode.SERVER_ERROR_CODE);
            }
        }
    }
}
