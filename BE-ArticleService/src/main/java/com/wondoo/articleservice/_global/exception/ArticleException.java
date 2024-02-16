package com.wondoo.articleservice._global.exception;

import lombok.Getter;

@Getter
public class ArticleException extends RuntimeException {
    private final int statusCode;
    private final String errorCode;
    private final String message;

    public ArticleException(ErrorCode code) {
        this.statusCode = code.getStatusCode();
        this.errorCode = code.getErrorCode();
        this.message = code.getMessage();
    }
}
