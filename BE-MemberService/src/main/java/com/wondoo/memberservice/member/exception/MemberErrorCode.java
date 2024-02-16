package com.wondoo.memberservice.member.exception;

import com.wondoo.memberservice.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum MemberErrorCode implements ErrorCode {

    MEMBER_NOT_FOUND(400, "Member_001", "사용자가 존재하지 않습니다."),
    INVALID_OPENAI_TOKEN(400, "MEMBER_02", "유효하지 않은 토큰입니다"),
    INVALID_GITHUB_TOKEN(400, "MEMBer_03", "유효하지 않은 토큰입니다")
    ;

    private final int status;
    private final String code;
    private final String message;

    MemberErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
