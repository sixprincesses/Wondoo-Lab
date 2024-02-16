package com.wondoo.articleservice.like.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LikeDeleteResponse(
        @JsonProperty("is_liked")
        boolean isLiked
) {
    public static LikeDeleteResponse init() {
        return new LikeDeleteResponse(false);
    }
}
