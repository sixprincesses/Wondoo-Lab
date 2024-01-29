package com.wondoo.memberservice.follow.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record FollowMessage(
        @JsonProperty("content")
        String content
) {
}
