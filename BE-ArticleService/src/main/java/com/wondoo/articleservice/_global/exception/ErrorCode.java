package com.wondoo.articleservice._global.exception;

public interface ErrorCode {
    int getStatusCode();

    String getErrorCode();

    String getMessage();
}
