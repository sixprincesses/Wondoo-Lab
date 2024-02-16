package com.wondoo.memberservice.auth.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public abstract class TokenResponse {
        @JsonProperty("access_token")
        protected String accessToken;
        @JsonProperty("refresh_token")
        protected String refreshToken;
}
