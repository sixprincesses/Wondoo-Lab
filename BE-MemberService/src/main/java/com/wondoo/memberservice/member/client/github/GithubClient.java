package com.wondoo.memberservice.member.client.github;

import com.wondoo.memberservice.member.client.github.data.request.GithubTokenRequest;
import com.wondoo.memberservice.member.client.github.data.response.GithubCodeResponse;
import com.wondoo.memberservice.member.client.github.data.response.GithubTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GithubClient {

    private final RestTemplate restTemplate;

    @Value("${github.client_id}")
    private String clientId;
    @Value("${github.client_secret}")
    private String clientSecret;

    // Github에 등록된 사용자 정보를 가져오기 위한 access_token 요청
    public GithubTokenResponse getAccessToken(GithubCodeResponse githubCodeResponse){

        GithubTokenRequest githubTokenRequest = GithubTokenRequest.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .code(githubCodeResponse.code())
                .build();

        return restTemplate.postForObject(
                "https://github.com/login/oauth/access_token",
                githubTokenRequest,
                GithubTokenResponse.class
        );
    }
}