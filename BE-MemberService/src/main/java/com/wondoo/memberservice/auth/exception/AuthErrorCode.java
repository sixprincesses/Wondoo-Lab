package com.wondoo.memberservice.auth.exception;

import com.wondoo.memberservice.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum AuthErrorCode implements ErrorCode {

    REFRESH_NOT_FOUND(400, "Auth_001", "재발급 요청 토큰이 존재하지 않습니다."),
    REFRESH_NOT_VALID(401, "Auth_002", "재발급 요청 토큰이 유효하지 않습니다.");

    private final int status;
    private final String code;
    private final String message;

    AuthErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
