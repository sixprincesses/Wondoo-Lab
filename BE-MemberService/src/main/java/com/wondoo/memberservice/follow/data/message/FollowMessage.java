package com.wondoo.memberservice.follow.data.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record FollowMessage(
        @JsonProperty("target_id")
        Long targetId,
        @JsonProperty("url")
        Long url,
        @JsonProperty("content")
        String content
) {
}
