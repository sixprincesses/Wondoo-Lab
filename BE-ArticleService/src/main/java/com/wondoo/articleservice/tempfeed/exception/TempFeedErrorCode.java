package com.wondoo.articleservice.tempfeed.exception;

import com.wondoo.articleservice._global.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum TempFeedErrorCode implements ErrorCode{
    TIME_LOG_ERROR(400, "TEMPFEED_001", "Time Log의 값이 존재하지 않습니다."),
    ;

    private final int statusCode;
    private final String errorCode;
    private final String message;

    TempFeedErrorCode(int statusCode, String errorCode, String message) {
        this.statusCode = statusCode;
        this.errorCode = errorCode;
        this.message = message;
    }
}
