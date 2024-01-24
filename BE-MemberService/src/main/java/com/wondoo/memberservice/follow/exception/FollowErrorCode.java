package com.wondoo.memberservice.follow.exception;

import com.wondoo.memberservice.global.exception.ErrorCode;
import lombok.Getter;

@Getter
public enum FollowErrorCode implements ErrorCode {

    FOLLOW_NOT_FOUND(400, "Follow_001", "follow not found"),
    FOLLOW_BAD_REQUEST(400, "Follow_002", "cannot follow yourself");

    private final int status;
    private final String code;
    private final String message;

    FollowErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
