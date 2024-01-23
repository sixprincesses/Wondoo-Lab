package com.wondoo.memberservice.member.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record MemberDetailResponse(
        @JsonProperty("nickname")
        String nickname,
        @JsonProperty("name")
        String name,
        @JsonProperty("email")
        String email
) {
}
