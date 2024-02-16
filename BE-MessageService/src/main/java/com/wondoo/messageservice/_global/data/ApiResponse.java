package com.wondoo.messageservice._global.data;

import com.wondoo.messageservice._global.data.StatusCode;
import lombok.Getter;

@Getter
public class ApiResponse<T> {
    private int status;
    private String message;
    private T response;

    public ApiResponse(StatusCode statusCode, T response) {
        this.status = statusCode.getStatus();
        this.message = statusCode.getMessage();
        this.response = response;
    }
}