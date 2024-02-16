package com.wondoo.articleservice.like.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LikeSaveResponse(
        @JsonProperty("is_liked")
        boolean isLiked
) {

    public static LikeSaveResponse init() {
        return new LikeSaveResponse(true);
    }
}
