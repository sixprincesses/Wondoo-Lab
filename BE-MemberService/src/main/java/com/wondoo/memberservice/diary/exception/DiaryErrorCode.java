package com.wondoo.memberservice.diary.exception;

import com.wondoo.memberservice.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum DiaryErrorCode implements ErrorCode {

    DIARY_NOT_FOUND(400, "DIARY_001", "다이어리가 존재하지 않습니다."),
    INVALID_MEMBER_REQUEST(401, "DIARY_02", "접근 권한이 없습니다"),
    ;

    private final int status;
    private final String code;
    private final String message;

    DiaryErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
