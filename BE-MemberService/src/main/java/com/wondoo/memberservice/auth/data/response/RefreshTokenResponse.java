package com.wondoo.memberservice.auth.data.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RefreshTokenResponse extends TokenResponse {
    @Builder
    public RefreshTokenResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
