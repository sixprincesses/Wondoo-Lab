package com.wondoo.memberservice.auth.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wondoo.memberservice.member.domain.Level;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginTokenResponse extends TokenResponse {
    @JsonProperty("member_id")
    private Long memberId;
    @JsonProperty("image_id")
    private String imageId;
    @JsonProperty("nickname")
    private String nickname;
    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("phone")
    private String phone;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("level")
    private Level level;

    @Builder
    public LoginTokenResponse(Long memberId, String imageId, String nickname, String name, String email, String phone, String gender, String accessToken, String refreshToken, Level level) {
        this.memberId = memberId;
        this.imageId = imageId;
        this.nickname = nickname;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.level = level;
    }
}
