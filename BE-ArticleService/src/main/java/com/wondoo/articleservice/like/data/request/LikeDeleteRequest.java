package com.wondoo.articleservice.like.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LikeDeleteRequest(
        @JsonProperty("feed_id")
        Long feedId
) {
}
