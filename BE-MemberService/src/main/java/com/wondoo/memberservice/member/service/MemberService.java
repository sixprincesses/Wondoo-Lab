package com.wondoo.memberservice.member.service;

import com.wondoo.memberservice.member.data.request.MemberUpdateRequest;
import com.wondoo.memberservice.member.data.response.MemberDetailResponse;
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
public class MemberService implements MemberSaveService, MemberLoadService {

    private final MemberRepository memberRepository;

    /**
     * Member 조회
     *
     * @param memberId Member PK
     * @return nickname, name, email 반환 / 추후 이미지 추가
     */
    @Override
    public MemberDetailResponse memberLoad(Long memberId) {

        Member member = checkMember(memberId);

        return MemberDetailResponse.builder()
                .nickname(member.getNickname())
                .name(member.getName())
                .email(member.getEmail())
                .build();
    }

    @Transactional
    @Override
    public void memberUpdate(Long memberId, Long socialId, MemberUpdateRequest memberUpdateRequest) {

        Member member = checkMember(memberId);
        validRequester(socialId, member);
        member.updateMemberInfo(
                memberUpdateRequest.nickname(),
                memberUpdateRequest.name(),
                memberUpdateRequest.email(),
                memberUpdateRequest.phone(),
                memberUpdateRequest.gender()
        );
    }

    private void validRequester(Long socialId, Member member) {
        if (!member.getSocialId().equals(socialId)) {
            throw new MemberException(MemberErrorCode.MEMBER_WRONG_ACCESS);
        }
    }

    private Member checkMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND)
                );
    }
}
