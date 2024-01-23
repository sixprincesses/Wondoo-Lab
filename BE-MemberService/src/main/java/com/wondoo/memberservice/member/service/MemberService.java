package com.wondoo.memberservice.member.service;

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
     * @param memberId Member PK
     * @return nickname, name, email 반환 / 추후 이미지 추가
     */
    @Override
    public MemberDetailResponse memberLoad(Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND)
                );
        return MemberDetailResponse.builder()
                .nickname(member.getNickname())
                .name(member.getName())
                .email(member.getEmail())
                .build();
    }
}
