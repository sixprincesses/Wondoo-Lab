package com.wondoo.articleservice.bookmark.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BookmarkSaveResponse(
        @JsonProperty("is_bookmarked")
        boolean isBookmarked
) {
    public static BookmarkSaveResponse init() {
        return new BookmarkSaveResponse(true);
    }
}
