package com.wondoo.articleservice.bookmark.service;

import com.wondoo.articleservice.bookmark.data.request.BookmarkDeleteRequest;
import com.wondoo.articleservice.bookmark.data.request.BookmarkSaveRequest;
import com.wondoo.articleservice.bookmark.data.response.BookmarkDeleteResponse;
import com.wondoo.articleservice.bookmark.data.response.BookmarkSaveResponse;

public interface BookmarkModifyService {
    BookmarkSaveResponse save(Long memberId, BookmarkSaveRequest request);

    BookmarkDeleteResponse delete(Long memberId, BookmarkDeleteRequest request);
}
