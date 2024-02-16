package com.wondoo.memberservice.global.exception;

import lombok.Getter;

@Getter
public enum ServerErrorCode implements ErrorCode{

    SERVER_ERROR_CODE(500, "Server_001", "서버에 오류가 발생했습니다.");

    private final int status;
    private final String code;
    private final String message;

    ServerErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
