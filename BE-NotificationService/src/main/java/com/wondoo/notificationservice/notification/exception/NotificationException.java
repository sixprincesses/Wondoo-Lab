package com.wondoo.notificationservice.notification.exception;

import com.wondoo.notificationservice.global.exception.CustomException;
import com.wondoo.notificationservice.global.exception.ErrorCode;

public class NotificationException extends CustomException {
    public NotificationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
