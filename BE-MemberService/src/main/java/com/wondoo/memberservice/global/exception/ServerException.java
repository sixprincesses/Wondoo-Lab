package com.wondoo.memberservice.global.exception;

public class ServerException extends CustomException{
    public ServerException(ErrorCode errorCode) {
        super(errorCode);
    }
}
