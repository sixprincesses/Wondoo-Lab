package com.wondoo.memberservice.member.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record BetweenServerResponse(
        @JsonProperty("social_id")
        Long social_id
) {
}
