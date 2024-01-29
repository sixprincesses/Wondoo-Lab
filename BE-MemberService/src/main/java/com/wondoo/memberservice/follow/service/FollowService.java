package com.wondoo.memberservice.follow.service;

import com.wondoo.memberservice.follow.domain.Follow;
import com.wondoo.memberservice.follow.exception.FollowErrorCode;
import com.wondoo.memberservice.follow.exception.FollowException;
import com.wondoo.memberservice.follow.message.FollowMessage;
import com.wondoo.memberservice.follow.repository.FollowRepository;
import com.wondoo.memberservice.global.utils.KafkaProvider;
import com.wondoo.memberservice.member.domain.Member;
import com.wondoo.memberservice.member.exception.MemberErrorCode;
import com.wondoo.memberservice.member.exception.MemberException;
import com.wondoo.memberservice.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService implements FollowSaveService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;
    private final KafkaProvider kafkaProvider;

    /**
     * 팔로우 로직 구현
     * 자기 자신을 팔로우 못하도록 제한
     *
     * @param memberId 팔로우 받는 사람
     * @param socialId 팔로우 요청하는 사람
     */
    @Transactional
    @Override
    public void memberFollow(Long memberId, Long socialId) {

        Member to = checkTo(memberId);

        if (to.getSocialId().equals(socialId)) {
            throw new FollowException(FollowErrorCode.FOLLOW_BAD_REQUEST);
        }

        Member from = checkFrom(socialId);

        followRepository.save(Follow.builder()
                .to(to)
                .from(from)
                .build());
        to.getStatistic()
                .followerCalculate(true);
        from.getStatistic()
                .followingCalculate(true);
        kafkaProvider.FollowMessage(
                FollowMessage.builder()
                        .targetId(to.getId())
                        .content(from.getNickname() + " 님이 회원님을 팔로우했습니다.")
                        .build()
        );
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

        Follow follow = followRepository.findById(
                        Follow.builder()
                                .to(to)
                                .from(from)
                                .build()
                                .getId())
                .orElseThrow(
                        () -> new FollowException(FollowErrorCode.FOLLOW_NOT_FOUND)
                );
        followRepository.delete(follow);
        to.getStatistic()
                .followerCalculate(false);
        from.getStatistic()
                .followingCalculate(false);
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
}
