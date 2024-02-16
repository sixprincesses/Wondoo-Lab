package com.wondoo.articleservice.global.fixture;

import com.wondoo.articleservice.bookmark.domain.Bookmark;

public enum BookmarkFixture {
    BOOKMARK_ID1(1L, 1L),
    BOOKMARK_ID2(2L, 1L),
    BOOKMARK_ID3(3L, 1L),
    BOOKMARK_ID1_MEMBER_ID2(1L, 2L),
    ;

    private final Long bookmarkId;
    private final Long memberId;

    BookmarkFixture(Long bookmarkId, Long memberId) {
        this.bookmarkId = bookmarkId;
        this.memberId = memberId;
    }

    public static Bookmark makeSavedBookmark(BookmarkFixture fixture, Long feedInfoId) {
        return Bookmark.builder()
                .id(fixture.bookmarkId)
                .memberId(fixture.memberId)
                .feedId(feedInfoId)
                .build();
    }

    public static Bookmark makeBookmark(BookmarkFixture fixture, Long feedInfoId) {
        return Bookmark.builder()
                .memberId(fixture.memberId)
                .feedId(feedInfoId)
                .build();
    }
}
