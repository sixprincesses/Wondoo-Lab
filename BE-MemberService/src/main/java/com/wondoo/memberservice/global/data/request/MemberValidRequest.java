package com.wondoo.memberservice.global.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record MemberValidRequest(
        @JsonProperty("social_id")
        Long socialId
) {
}
