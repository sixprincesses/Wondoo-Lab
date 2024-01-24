package com.wondoo.memberservice.auth.service;

import com.wondoo.memberservice.auth.data.request.MemberTokenRequest;
import com.wondoo.memberservice.auth.data.request.RefreshRelatedRequest;
import com.wondoo.memberservice.auth.data.response.TokenMarker;
import com.wondoo.memberservice.auth.domain.RefreshToken;
import com.wondoo.memberservice.auth.exception.AuthErrorCode;
import com.wondoo.memberservice.auth.exception.AuthException;
import com.wondoo.memberservice.auth.repository.RefreshTokenRepository;
import com.wondoo.memberservice.auth.utils.TokenProvider;
import com.wondoo.memberservice.auth.client.github.data.response.GithubUserInfoResponse;
import com.wondoo.memberservice.member.domain.Member;
import com.wondoo.memberservice.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenProvider tokenProvider;

    /**
     * 회원가입 / 로그인
     *
     * @param githubUserInfoResponse Github 에 등록된 사용자 정보
     * @return 회원가입은 member_id와 함께 JWT 반환, 로그인은 JWT 만 반환
     */
    @Transactional
    public ResponseEntity<TokenMarker> memberSave(GithubUserInfoResponse githubUserInfoResponse) {

        Optional<Member> tmpMember = memberRepository.findBySocialId(githubUserInfoResponse.socialId());

        if (tmpMember.isEmpty()) {
            TokenMarker tokenMarker = getTokenMarkerForSignup(githubUserInfoResponse);

            return ResponseEntity.status(HttpStatus.CREATED).body(tokenMarker);
        }

        Member member = tmpMember.get();
        // Github Nickname 을 변경했으면 반영
        if (isDifferentPreSocialNickname(githubUserInfoResponse, member)) {
            member.updateSocialNickname(githubUserInfoResponse.socialNickname());
        }

        TokenMarker tokenMarker = tokenProvider.jwtSave(MemberTokenRequest.builder()
                .socialId(member.getSocialId())
                .build());

        return ResponseEntity.status(HttpStatus.OK).body(tokenMarker);
    }

    /**
     * 회원 로그아웃
     * refresh_token 검증 후 제거로 로그아웃 처리
     *
     * @param socialId            헤더에 담은 social_id
     * @param refreshRelatedRequest 클라이언트가 관리하는 refresh_token
     */
    @Transactional
    public void memberLogout(
            Long socialId,
            RefreshRelatedRequest refreshRelatedRequest
    ) {

        RefreshToken refreshToken = refreshTokenRepository.findById(socialId)
                .orElseThrow(
                        () -> new AuthException(AuthErrorCode.REFRESH_NOT_FOUND)
                );

        if (isDifferentRefresh(refreshRelatedRequest, refreshToken)) {
            throw new AuthException(AuthErrorCode.REFRESH_NOT_VALID);
        }
        refreshTokenRepository.delete(refreshToken);
    }

    /**
     * JWT Refresh 요청
     * refresh_token 검증 후 JWT 재발급
     * @param socialId 헤더에 담은 social_id
     * @param refreshRelatedRequest 클라이언트가 관리하는 refresh_token
     * @return JWT 반환
     */
    @Transactional
    public TokenMarker memberRefresh(
            Long socialId,
            RefreshRelatedRequest refreshRelatedRequest
    ){

        RefreshToken refreshToken = refreshTokenRepository.findById(socialId)
                .orElseThrow(
                        () -> new AuthException(AuthErrorCode.REFRESH_NOT_FOUND)
                );

        if (isDifferentRefresh(refreshRelatedRequest, refreshToken)) {
            throw new AuthException(AuthErrorCode.REFRESH_NOT_VALID);
        }

        return tokenProvider.jwtSave(MemberTokenRequest.builder()
                .socialId(socialId)
                .build());
    }

    private boolean isDifferentRefresh(RefreshRelatedRequest refreshRelatedRequest, RefreshToken refreshToken) {
        return !refreshRelatedRequest.refreshToken()
                .equals(refreshToken.getRefreshToken());
    }

    private TokenMarker getTokenMarkerForSignup(GithubUserInfoResponse githubUserInfoResponse) {
        Member member = memberRepository.save(Member.builder()
                .socialId(githubUserInfoResponse.socialId())
                .socialNickname(githubUserInfoResponse.socialNickname())
                .build());

        return tokenProvider.jwtSave(MemberTokenRequest.builder()
                .memberId(member.getId())
                .socialId(member.getSocialId())
                .build());
    }

    private boolean isDifferentPreSocialNickname(GithubUserInfoResponse githubUserInfoResponse, Member member) {
        return !githubUserInfoResponse.socialNickname().equals(member.getSocialNickname());
    }
}
