package com.wondoo.memberservice.auth.client.github.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record GithubTokenResponse(
        @JsonProperty("access_token")
        String accessToken
) {
}
