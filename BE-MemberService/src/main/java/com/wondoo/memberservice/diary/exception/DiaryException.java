package com.wondoo.memberservice.diary.exception;

import com.wondoo.memberservice.global.exception.CustomException;
import com.wondoo.memberservice.global.exception.ErrorCode;

public class DiaryException extends CustomException {
    public DiaryException(ErrorCode errorCode) {
        super(errorCode);
    }
}
