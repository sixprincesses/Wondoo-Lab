package com.wondoo.articleservice.bookmark.service;

import com.wondoo.articleservice.bookmark.data.request.BookmarksRequest;
import com.wondoo.articleservice.bookmark.data.response.BookmarksResponse;

public interface BookmarkLoadService {
    /**
     * {@link BookmarksResponse}에서 `lastBookmarkId`이 `-1`인 경우에는 더 이상 데이터가 없다는 뜻입니다.
     */
    BookmarksResponse findBookmarks(Long memberId, BookmarksRequest request);
}
