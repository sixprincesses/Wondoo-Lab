package com.wondoo.memberservice.member.service;

import com.wondoo.memberservice.auth.data.request.MemberTokenRequest;
import com.wondoo.memberservice.auth.data.response.LoginTokenResponse;
import com.wondoo.memberservice.auth.data.response.SignupTokenResponse;
import com.wondoo.memberservice.auth.data.response.TokenMarker;
import com.wondoo.memberservice.auth.utils.TokenProvider;
import com.wondoo.memberservice.member.client.github.data.response.GithubUserInfoResponse;
import com.wondoo.memberservice.member.domain.Member;
import com.wondoo.memberservice.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService implements MemberSaveService {

    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    /**
     * 회원가입 / 로그인
     *
     * @param githubUserInfoResponse Github 에 등록된 사용자 정보
     * @return 회원가입은 member_id와 함께 JWT 반환, 로그인은 JWT 만 반환
     */
    @Transactional
    @Override
    public ResponseEntity<TokenMarker> memberSave(GithubUserInfoResponse githubUserInfoResponse) {

        Optional<Member> tmpMember = memberRepository.findBySocialId(githubUserInfoResponse.socialId());

        if (tmpMember.isEmpty()) {
            Member member = memberRepository.save(Member.builder()
                    .socialId(githubUserInfoResponse.socialId())
                    .socialNickname(githubUserInfoResponse.socialNickname())
                    .build());

            TokenMarker tokenMarker = tokenProvider.jwtSave(MemberTokenRequest.builder()
                    .memberId(member.getId())
                    .socialId(member.getSocialId())
                    .build());

            return ResponseEntity.status(HttpStatus.CREATED).body(tokenMarker);
        }

        // Github Nickname 을 변경했으면 반영
        if (isDifferentPreSocialNickname(githubUserInfoResponse, tmpMember)) {
            tmpMember.get().updateSocialNickname(githubUserInfoResponse.socialNickname());
        }

        TokenMarker tokenMarker = tokenProvider.jwtSave(MemberTokenRequest.builder()
                .socialId(tmpMember.get().getSocialId())
                .build());

        return ResponseEntity.status(HttpStatus.OK).body(tokenMarker);
    }

    private boolean isDifferentPreSocialNickname(GithubUserInfoResponse githubUserInfoResponse, Optional<Member> tmpMember) {
        return !githubUserInfoResponse.socialNickname().equals(tmpMember.get().getSocialNickname());
    }
}
