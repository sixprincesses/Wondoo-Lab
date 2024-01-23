package com.wondoo.memberservice.member.exception;

import com.wondoo.memberservice.global.exception.CustomException;
import com.wondoo.memberservice.global.exception.ErrorCode;

public class MemberException extends CustomException {
    public MemberException(ErrorCode errorCode) {
        super(errorCode);
    }
}
