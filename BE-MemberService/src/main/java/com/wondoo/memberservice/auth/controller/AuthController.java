package com.wondoo.memberservice.auth.controller;

import com.wondoo.memberservice.auth.data.request.MemberLogoutRequest;
import com.wondoo.memberservice.auth.data.response.TokenMarker;
import com.wondoo.memberservice.auth.service.AuthService;
import com.wondoo.memberservice.auth.client.github.GithubClient;
import com.wondoo.memberservice.auth.client.github.data.response.GithubCodeResponse;
import com.wondoo.memberservice.auth.client.github.data.response.GithubTokenResponse;
import com.wondoo.memberservice.auth.client.github.data.response.GithubUserInfoResponse;
import com.wondoo.memberservice.global.data.request.MemberValidRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final GithubClient githubClient;
    private final AuthService authService;

    @PostMapping("/member/login")
    public ResponseEntity<TokenMarker> memberLogin(@RequestBody @Valid GithubCodeResponse githubCodeResponse) {

        GithubTokenResponse githubTokenResponse = githubClient.getAccessToken(githubCodeResponse);
        GithubUserInfoResponse githubUserInfoResponse = githubClient.getUserInfo(githubTokenResponse);

        return authService.memberSave(githubUserInfoResponse);
    }

    @PostMapping("/member/logout")
    public ResponseEntity<String> memberLogout(
            @RequestHeader("social_id") Long socialId,
            @RequestBody @Valid MemberLogoutRequest memberLogoutRequest
    ) {

        authService.memberLogout(MemberValidRequest.builder()
                .socialId(socialId)
                .build(), memberLogoutRequest);

        return ResponseEntity.ok("Logout Success");
    }
}
