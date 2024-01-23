package com.wondoo.memberservice.auth.data.response;

import lombok.Builder;

public class LoginTokenResponse extends TokenMarker {

    @Builder
    public LoginTokenResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
