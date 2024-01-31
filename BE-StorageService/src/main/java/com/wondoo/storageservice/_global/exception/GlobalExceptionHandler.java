package com.wondoo.storageservice._global.exception;

import com.wondoo.storageservice._global.data.ApiResponse;
import com.wondoo.storageservice._global.data.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected final ResponseEntity<ApiResponse<Void>> handleAllExceptions(Exception exception){
        ApiResponse<Void> response = new ApiResponse<>(StatusCode.ERROR_BAD_REQUEST, null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}