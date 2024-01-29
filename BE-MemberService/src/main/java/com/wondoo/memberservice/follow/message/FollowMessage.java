package com.wondoo.memberservice.follow.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record FollowMessage(
        @JsonProperty("target_id")
        Long targetId,
        @JsonProperty("content")
        String content
) {
}
