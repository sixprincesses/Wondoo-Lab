package com.wondoo.memberservice.auth.controller;

import com.wondoo.memberservice.auth.data.response.TokenMarker;
import com.wondoo.memberservice.auth.service.AuthService;
import com.wondoo.memberservice.member.client.github.GithubClient;
import com.wondoo.memberservice.member.client.github.data.response.GithubCodeResponse;
import com.wondoo.memberservice.member.client.github.data.response.GithubTokenResponse;
import com.wondoo.memberservice.member.client.github.data.response.GithubUserInfoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
}
