package com.wondoo.memberservice.member.service;

import com.wondoo.memberservice.member.data.request.MemberUpdateRequest;
import com.wondoo.memberservice.member.data.response.BetweenServerResponse;
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
    public MemberDetailResponse memberDetailLoad(Long memberId) {

        Member member = findById(memberId);

        return MemberDetailResponse.builder()
                .nickname(member.getNickname())
                .name(member.getName())
                .email(member.getEmail())
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
    public void memberUpdate(Long memberId, Long socialId, MemberUpdateRequest memberUpdateRequest) {

        Member member = findById(memberId);
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

    private Member findById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND)
                );
    }
}
