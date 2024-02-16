package com.wondoo.articleservice.tempfeed.controller;

import com.wondoo.articleservice._global.annotation.RestWondooController;
import com.wondoo.articleservice._global.data.ApiResponse;
import com.wondoo.articleservice._global.data.StatusCode;
import com.wondoo.articleservice.tempfeed.data.request.TempFeedSaveRequest;
import com.wondoo.articleservice.tempfeed.data.response.TempFeedLoadResponse;
import com.wondoo.articleservice.tempfeed.service.TempFeedLoadService;
import com.wondoo.articleservice.tempfeed.service.TempFeedSaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestWondooController
@RequiredArgsConstructor
@Slf4j
public class TempFeedController {

    private final TempFeedSaveService tempFeedSaveService;
    private final TempFeedLoadService tempFeedLoadService;

    @GetMapping("/auth/tempfeed")
    public ResponseEntity<ApiResponse<TempFeedLoadResponse>> tempFeedLoad(
            @RequestHeader("member_id") Long memberId
    ) {

        TempFeedLoadResponse tempFeedLoadResponse = tempFeedLoadService.tempFeedLoad(memberId);
        ApiResponse<TempFeedLoadResponse> response = new ApiResponse<>(StatusCode.SUCCESS_SELECT, tempFeedLoadResponse);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/auth/tempfeed")
    public ResponseEntity<ApiResponse<Void>> tempFeedSave(
            @RequestHeader("member_id") Long memberId,
            @RequestBody @Valid TempFeedSaveRequest tempFeedSaveRequest
    ) {

        tempFeedSaveService.tempFeedSave(memberId, tempFeedSaveRequest);
        ApiResponse<Void> response = new ApiResponse<>(StatusCode.SUCCESS_INSERT, null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/auth/tempfeed")
    public ResponseEntity<ApiResponse<Void>> tempFeedDelete(
            @RequestHeader("member_id") Long memberId
    ) {

        tempFeedSaveService.tempFeedDelete(memberId);
        ApiResponse<Void> response = new ApiResponse<>(StatusCode.SUCCESS_DELETE, null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
