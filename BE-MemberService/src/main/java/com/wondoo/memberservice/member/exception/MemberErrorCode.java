package com.wondoo.memberservice.member.exception;

import com.wondoo.memberservice.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum MemberErrorCode implements ErrorCode {

    MEMBER_NOT_FOUND(400, "Member_001", "member is not found"),
    MEMBER_WRONG_ACCESS(401, "Member_002", "cannot access logic");

    private final int status;
    private final String code;
    private final String message;

    MemberErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
