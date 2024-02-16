package com.wondoo.articleservice.like.data.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record LikeMessage(
        @JsonProperty("target_id")
        Long targetId,
        @JsonProperty("url")
        Long url,
        @JsonProperty("content")
        String content
) {
}
