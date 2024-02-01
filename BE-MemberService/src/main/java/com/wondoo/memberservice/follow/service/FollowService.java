package com.wondoo.memberservice.follow.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wondoo.memberservice.follow.data.message.FollowMessage;
import com.wondoo.memberservice.follow.data.query.FollowsResponse;
import com.wondoo.memberservice.follow.domain.Follow;
import com.wondoo.memberservice.follow.exception.FollowErrorCode;
import com.wondoo.memberservice.follow.exception.FollowException;
import com.wondoo.memberservice.follow.repository.FollowRepository;
import com.wondoo.memberservice.global.utils.KafkaProducer;
import com.wondoo.memberservice.member.domain.Member;
import com.wondoo.memberservice.member.exception.MemberErrorCode;
import com.wondoo.memberservice.member.exception.MemberException;
import com.wondoo.memberservice.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class FollowService implements FollowSaveService, FollowLoadService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;
    private final KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper;

    /**
     * 팔로우 로직 구현
     * 자기 자신을 팔로우 못하도록 제한
     *
     * @param memberId 팔로우 받는 사람
     * @param socialId 팔로우 요청하는 사람
     */
    @Transactional
    @Override
    public void memberFollow(Long memberId, Long socialId) throws JsonProcessingException {

        Member to = checkTo(memberId);

        if (to.getSocialId().equals(socialId)) {
            throw new FollowException(FollowErrorCode.FOLLOW_BAD_REQUEST);
        }

        Member from = checkFrom(socialId);

        Optional<Follow> followChk = followRepository.findByFollow(to.getId(), from.getId());
        if (followChk.isEmpty()){
            followRepository.save(Follow.builder()
                    .to(to)
                    .from(from)
                    .build());
            to.getStatistic()
                    .followerCalculate(true);
            from.getStatistic()
                    .followingCalculate(true);

            kafkaProducer.sendMessage(
                    objectMapper.writeValueAsString(
                            FollowMessage.builder()
                                    .targetId(to.getId())
                                    .content(from.getNickname() + " 님이 회원님을 팔로우했습니다.")
                                    .build()
                    )
            );
            return;
        }
        throw new FollowException(FollowErrorCode.FOLLOW_DUPLICATE_REQUEST);

    }

    /**
     * 언팔로우 로직 구현
     * 자기 자신을 언팔로우 못하도록 제한
     * 이미 팔로우 한 상태인지 확인
     *
     * @param memberId 팔로우 받는 사람
     * @param socialId 팔로우 요청하는 사람
     */
    @Transactional
    @Override
    public void memberUnfollow(Long memberId, Long socialId) {

        Member to = checkTo(memberId);
        if (to.getSocialId().equals(socialId)) {
            throw new FollowException(FollowErrorCode.FOLLOW_BAD_REQUEST);
        }

        Member from = checkFrom(socialId);
        Follow follow = followRepository.findByFollow(to.getId(), from.getId())
                .orElseThrow(
                        () -> new FollowException(FollowErrorCode.FOLLOW_NOT_FOUND)
                );
        followRepository.delete(follow);
        to.getStatistic()
                .followerCalculate(false);
        from.getStatistic()
                .followingCalculate(false);
    }

    /**
     * 페이지네이션 정보 기반 팔로워 조회
     * 현재 팔로워 닉네임만 조회되는데 추후 이미지도 조회 가능하도록 수정 예정
     *
     * @param memberId 조회할 기준 member_id
     * @param pageable 페이지네이션 정보 (page_size, page_offset)
     * @return Page 정보
     */
    @Override
    public Page<FollowsResponse> followersLoad(Long memberId, Pageable pageable) {

        findById(memberId);
        return followRepository.followersLoad(memberId, pageable);
    }

    /**
     * 페이지네이션 정보 기반 팔로잉 조회
     * 현재 팔로잉 닉네임만 조회되는데 추후 이미지도 조회 가능하도록 수정 예정
     *
     * @param memberId 조회할 기준 member_id
     * @param pageable 페이지네이션 정보 (page_size, page_offset)
     * @return Page 정보
     */
    @Override
    public Page<FollowsResponse> followingsLoad(Long memberId, Pageable pageable) {

        findById(memberId);
        return followRepository.followingsLoad(memberId, pageable);
    }

    private Member checkFrom(Long socialId) {
        return memberRepository.findBySocialId(socialId)
                .orElseThrow(
                        () -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND)
                );
    }

    private Member checkTo(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND)
                );
    }

    private Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND)
                );
    }
}
