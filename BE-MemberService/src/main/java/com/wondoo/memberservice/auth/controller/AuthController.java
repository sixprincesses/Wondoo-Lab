package com.wondoo.memberservice.auth.controller;

import com.wondoo.memberservice.auth.client.github.GithubClient;
import com.wondoo.memberservice.auth.client.github.data.response.GithubCodeResponse;
import com.wondoo.memberservice.auth.client.github.data.response.GithubTokenResponse;
import com.wondoo.memberservice.auth.client.github.data.response.GithubUserInfoResponse;
import com.wondoo.memberservice.auth.data.request.RefreshRelatedRequest;
import com.wondoo.memberservice.auth.data.response.TokenResponse;
import com.wondoo.memberservice.auth.service.AuthService;
import com.wondoo.memberservice.global.annotation.RestWondooController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@RestWondooController
@RequiredArgsConstructor
public class AuthController {

    private final GithubClient githubClient;
    private final AuthService authService;

    @PostMapping("/member/signin")
    public ResponseEntity<TokenResponse> memberLogin(@RequestBody @Valid GithubCodeResponse githubCodeResponse) {

        GithubTokenResponse githubTokenResponse = githubClient.getAccessToken(githubCodeResponse);
        GithubUserInfoResponse githubUserInfoResponse = githubClient.getUserInfo(githubTokenResponse);

        return authService.memberSave(githubUserInfoResponse);
    }

    @PostMapping("/auth/member/logout")
    public ResponseEntity<String> memberLogout(
            @RequestHeader("member_id") String memberId,
            @RequestBody @Valid RefreshRelatedRequest refreshRelatedRequest
    ) {

        authService.memberLogout(Long.valueOf(memberId), refreshRelatedRequest);

        return ResponseEntity.ok("Logout Success");
    }

    @PostMapping("/auth/member/refresh")
    public ResponseEntity<TokenResponse> memberRefresh(
            @RequestHeader("member_id") Long memberId,
            @RequestBody @Valid RefreshRelatedRequest refreshRelatedRequest
    ) {

        return ResponseEntity.ok(authService.memberRefresh(memberId, refreshRelatedRequest));
    }
}
