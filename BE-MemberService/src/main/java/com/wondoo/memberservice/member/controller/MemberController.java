package com.wondoo.memberservice.member.controller;

import com.wondoo.memberservice.auth.data.response.TokenMarker;
import com.wondoo.memberservice.member.client.github.GithubClient;
import com.wondoo.memberservice.member.client.github.data.response.GithubCodeResponse;
import com.wondoo.memberservice.member.client.github.data.response.GithubTokenResponse;
import com.wondoo.memberservice.member.client.github.data.response.GithubUserInfoResponse;
import com.wondoo.memberservice.member.service.MemberSaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final GithubClient githubClient;
    private final MemberSaveService memberSaveService;

    @PostMapping("/member/login")
    public ResponseEntity<TokenMarker> memberLogin(@RequestBody @Valid GithubCodeResponse githubCodeResponse){

        GithubTokenResponse githubTokenResponse = githubClient.getAccessToken(githubCodeResponse);
        GithubUserInfoResponse githubUserInfoResponse = githubClient.getUserInfo(githubTokenResponse);

        return memberSaveService.memberSave(githubUserInfoResponse);
    }
}
