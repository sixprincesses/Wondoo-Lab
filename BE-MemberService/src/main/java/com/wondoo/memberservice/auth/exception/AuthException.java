package com.wondoo.memberservice.auth.exception;

import com.wondoo.memberservice.global.exception.CustomException;
import com.wondoo.memberservice.global.exception.ErrorCode;

public class AuthException extends CustomException {
    public AuthException(ErrorCode errorCode) {
        super(errorCode);
    }
}
