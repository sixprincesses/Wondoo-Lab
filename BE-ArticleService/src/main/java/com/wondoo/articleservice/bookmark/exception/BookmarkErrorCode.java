package com.wondoo.articleservice.bookmark.exception;

import com.wondoo.articleservice._global.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum BookmarkErrorCode implements ErrorCode {
    NON_EXISTED_BOOKMARK(400, "BOOKMARK_001", "존재하지 않는 북마크입니다."),
    NO_OWNERSHIP(403, "BOOKMARK_002", "해당 북마크의 소유권이 없습니다."),
    ALREADY_BOOKMARKED_FEED(400, "BOOKMARK_003", "이미 북마크한 피드입니다.");

    private final int statusCode;
    private final String errorCode;
    private final String message;

    BookmarkErrorCode(int statusCode, String errorCode, String message) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.message = message;
    }
}
