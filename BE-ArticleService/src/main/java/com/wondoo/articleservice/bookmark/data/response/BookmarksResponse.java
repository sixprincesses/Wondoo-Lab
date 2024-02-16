package com.wondoo.articleservice.bookmark.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record BookmarksResponse(
        @JsonProperty("bookmarks")
        List<BookmarkResponse> bookmarks,
        @JsonProperty("last_bookmark_id")
        Long lastBookmarkId
) {
    public static BookmarksResponse of(List<BookmarkResponse> bookmarks, Long lastBookmarkId) {
        return BookmarksResponse.builder()
                .bookmarks(bookmarks)
                .lastBookmarkId(lastBookmarkId)
                .build();
    }
}
