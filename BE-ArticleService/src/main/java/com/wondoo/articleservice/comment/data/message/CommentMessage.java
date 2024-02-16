package com.wondoo.articleservice.comment.data.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record CommentMessage(
        @JsonProperty("target_id")
        Long targetId,
        @JsonProperty("url")
        Long url,
        @JsonProperty("sub_url")
        Long subUrl,
        @JsonProperty("content")
        String content
) {
}
