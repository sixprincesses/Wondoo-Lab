package com.wondoo.memberservice.follow.exception;

import com.wondoo.memberservice.global.exception.CustomException;
import com.wondoo.memberservice.global.exception.ErrorCode;

public class FollowException extends CustomException {
    public FollowException(ErrorCode errorCode) {
        super(errorCode);
    }
}
