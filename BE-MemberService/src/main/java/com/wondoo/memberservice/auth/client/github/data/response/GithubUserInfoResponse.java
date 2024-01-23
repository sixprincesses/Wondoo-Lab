package com.wondoo.memberservice.auth.client.github.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record GithubUserInfoResponse(
        @JsonProperty("id")
        Long socialId,
        @JsonProperty("login")
        String socialNickname
) {
}
