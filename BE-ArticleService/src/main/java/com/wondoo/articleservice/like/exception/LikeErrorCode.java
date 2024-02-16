package com.wondoo.articleservice.like.exception;

import com.wondoo.articleservice._global.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum LikeErrorCode implements ErrorCode {
    NON_EXISTED_LIKE(400, "LIKE_001", "존재하지 않는 좋아요입니다."),
    NO_OWNERSHIP(403, "LIKE_002", "해당 좋아요의 소유권이 없습니다."),
    ALREADY_LIKE_FEED(400, "LIKE_003", "이미 좋아요한 피드입니다.");;

    private final int statusCode;
    private final String errorCode;
    private final String message;

    LikeErrorCode(int statusCode, String errorCode, String message) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.message = message;
    }
}
