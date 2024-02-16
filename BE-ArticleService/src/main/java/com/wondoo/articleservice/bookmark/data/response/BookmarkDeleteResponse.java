package com.wondoo.articleservice.bookmark.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BookmarkDeleteResponse(
        @JsonProperty("is_bookmarked")
        boolean isBookmarked
) {
    public static BookmarkDeleteResponse init() {
        return new BookmarkDeleteResponse(false);
    }
}
