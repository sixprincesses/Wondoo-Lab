package com.wondoo.memberservice.auth.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record MemberTokenRequest(
        @JsonProperty("social_id")
        Long socialId
) {
}
