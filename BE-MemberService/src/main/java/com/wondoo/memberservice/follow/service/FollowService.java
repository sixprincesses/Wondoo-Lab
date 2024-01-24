package com.wondoo.memberservice.follow.service;

import com.wondoo.memberservice.follow.domain.Follow;
import com.wondoo.memberservice.follow.exception.FollowErrorCode;
import com.wondoo.memberservice.follow.exception.FollowException;
import com.wondoo.memberservice.follow.repository.FollowRepository;
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
