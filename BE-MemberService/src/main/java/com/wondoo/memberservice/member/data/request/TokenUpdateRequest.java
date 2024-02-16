package com.wondoo.memberservice.member.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TokenUpdateRequest(
        @JsonProperty("token")
        String token
) {
}
