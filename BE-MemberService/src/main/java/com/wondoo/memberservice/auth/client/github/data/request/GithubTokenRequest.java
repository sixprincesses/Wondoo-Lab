package com.wondoo.memberservice.auth.client.github.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record GithubTokenRequest(
        @JsonProperty("client_id")
        String clientId,
        @JsonProperty("client_secret")
        String clientSecret,
        @JsonProperty("code")
        String code
) {
}
