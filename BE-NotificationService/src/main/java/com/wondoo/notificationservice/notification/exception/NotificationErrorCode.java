package com.wondoo.notificationservice.notification.exception;

import com.wondoo.notificationservice.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum NotificationErrorCode implements ErrorCode {

    NOTIFICATION_NOT_FOUND(400, "Notification_001", "알림이 존재하지 않습니다."),
    NOTIFICATION_WRONG_ACCESS(401, "Notification_002", "접근할 수 없는 서비스입니다.");

    private final int status;
    private final String code;
    private final String message;

    NotificationErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
