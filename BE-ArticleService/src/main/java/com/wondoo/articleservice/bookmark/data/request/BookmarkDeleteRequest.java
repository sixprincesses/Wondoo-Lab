package com.wondoo.articleservice.bookmark.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BookmarkDeleteRequest(
        @JsonProperty("feed_id")
        Long feedId
) {
}
