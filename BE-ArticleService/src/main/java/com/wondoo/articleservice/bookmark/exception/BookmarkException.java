package com.wondoo.articleservice.bookmark.exception;

import com.wondoo.articleservice._global.exception.ArticleException;
import com.wondoo.articleservice._global.exception.ErrorCode;

public class BookmarkException extends ArticleException {
    public BookmarkException(ErrorCode errorCode) {
        super(errorCode);
    }
}
