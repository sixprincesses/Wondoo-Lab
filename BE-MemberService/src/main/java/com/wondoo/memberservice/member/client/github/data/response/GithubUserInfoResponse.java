package com.wondoo.memberservice.member.client.github.data.response;

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
