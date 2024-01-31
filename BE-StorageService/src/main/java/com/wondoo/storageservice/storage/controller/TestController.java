package com.wondoo.storageservice.storage.controller;

import com.wondoo.storageservice._global.data.ApiResponse;
import com.wondoo.storageservice._global.data.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/storage-service")
@Slf4j
public class TestController {
    @GetMapping("/test/success")
    public ResponseEntity<ApiResponse<Void>> success(){
        log.info("TEST SUCCESS");
        ApiResponse<Void> response = new ApiResponse<>(StatusCode.SUCCESS_TEST, null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    @GetMapping("/test/error")
    public ResponseEntity<ApiResponse<Void>> error() throws Exception {
        log.info("TEST ERROR");
        throw new Exception("테스트 Exception Error");
    }
}
