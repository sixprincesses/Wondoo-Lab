package com.wondoo.notificationservice.global.exception;

public interface ErrorCode {

    int getStatus();

    String getCode();

    String getMessage();
}
