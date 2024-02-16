package com.wondoo.memberservice.member.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import lombok.Builder;

@Builder
public record MemberUpdateResponse(
        @JsonProperty("nickname")
        String nickname,
        @JsonProperty("name")
        String name,
        @Email
        @JsonProperty("email")
        String email,
        @JsonProperty("phone")
        String phone,
        @JsonProperty("gender")
        String gender
) {
}
