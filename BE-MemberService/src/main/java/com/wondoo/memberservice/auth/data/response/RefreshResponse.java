package com.wondoo.memberservice.auth.data.response;

import lombok.Builder;

public class RefreshResponse extends TokenMarker {

    @Builder
    public RefreshResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
