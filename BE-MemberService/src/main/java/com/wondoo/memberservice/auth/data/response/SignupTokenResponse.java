package com.wondoo.memberservice.auth.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SignupTokenResponse extends TokenMarker {

    @JsonProperty("member_id")
    private Long memberId;

    @Builder
    public SignupTokenResponse(Long memberId, String accessToken, String refreshToken) {
        this.memberId = memberId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
