package com.wondoo.memberservice.member.controller;

import com.wondoo.memberservice.member.client.github.GithubClient;
import com.wondoo.memberservice.member.client.github.data.response.GithubCodeResponse;
import com.wondoo.memberservice.member.client.github.data.response.GithubTokenResponse;
import com.wondoo.memberservice.member.client.github.data.response.GithubUserInfoResponse;
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

    @PostMapping("/member/login")
    public ResponseEntity<String> memberLogin(@RequestBody @Valid GithubCodeResponse githubCodeResponse){

        GithubTokenResponse githubTokenResponse = githubClient.getAccessToken(githubCodeResponse);
        GithubUserInfoResponse githubClientUserInfo = githubClient.getUserInfo(githubTokenResponse);

        return ResponseEntity.ok("success");
    }
}
