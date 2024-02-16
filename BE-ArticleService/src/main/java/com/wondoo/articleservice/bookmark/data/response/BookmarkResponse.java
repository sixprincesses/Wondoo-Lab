package com.wondoo.articleservice.bookmark.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wondoo.articleservice.bookmark.domain.Bookmark;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record BookmarkResponse(
        @JsonProperty("bookmark_id")
        Long bookmarkId,
        @JsonProperty("feed_id")
        Long feedId,
        @JsonProperty("title")
        String feedTitle,
        @JsonProperty("member")
        BookmarkMemberResponse member,
        @JsonProperty("created_time")
        LocalDateTime createdTime
) {
    public static BookmarkResponse from(Bookmark bookmark) {
        return BookmarkResponse.builder()
                .bookmarkId(bookmark.getId())
                .feedId(bookmark.getFeedinfo().getId())
                .member(BookmarkMemberResponse.from(bookmark.getMemberInfo()))
                .feedTitle(bookmark.getFeedinfo().getTitle())
                .createdTime(bookmark.getUpdatedTime())
                .build();
    }
}
