package com.wondoo.memberservice.auth.client.github;

import com.wondoo.memberservice.auth.client.github.data.request.GithubTokenRequest;
import com.wondoo.memberservice.auth.client.github.data.response.GithubCodeResponse;
import com.wondoo.memberservice.auth.client.github.data.response.GithubTokenResponse;
import com.wondoo.memberservice.auth.client.github.data.response.GithubUserInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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


    /**
     * Github 에 등록된 사용자 정보를 가져오기 위한 access_token 요청
     *
     * @param githubCodeResponse OAuth Callback Code
     * @return 사용자의 access_token
     */

    public GithubTokenResponse getAccessToken(GithubCodeResponse githubCodeResponse) {

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

    /**
     * Github에 등록된 사용자 정보 반환
     *
     * @param githubTokenResponse Github access_token
     * @return 사용자의 id와 login 정보
     */
    public GithubUserInfoResponse getUserInfo(GithubTokenResponse githubTokenResponse) {

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + githubTokenResponse.accessToken());

        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        return restTemplate.exchange(
                "https://api.github.com/user",
                HttpMethod.GET,
                entity,
                GithubUserInfoResponse.class
        ).getBody();
    }
}