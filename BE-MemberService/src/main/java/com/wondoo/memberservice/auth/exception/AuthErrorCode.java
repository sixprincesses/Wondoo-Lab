package com.wondoo.memberservice.auth.exception;

import com.wondoo.memberservice.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum AuthErrorCode implements ErrorCode {

    REFRESH_NOT_FOUND(400, "Auth_001", "refresh is not found"),
    REFRESH_NOT_VALID(401, "Auth_002", "refresh is not valid");

    private final int status;
    private final String code;
    private final String message;

    AuthErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
