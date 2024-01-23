package com.wondoo.memberservice.auth.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record MemberLogoutRequest(
        @JsonProperty("refresh_token")
        String refreshToken
) {
}
