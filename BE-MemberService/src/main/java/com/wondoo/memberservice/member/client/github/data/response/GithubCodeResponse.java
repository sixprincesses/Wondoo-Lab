package com.wondoo.memberservice.member.client.github.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record GithubCodeResponse(
        @JsonProperty("code")
        String code
) {
}
