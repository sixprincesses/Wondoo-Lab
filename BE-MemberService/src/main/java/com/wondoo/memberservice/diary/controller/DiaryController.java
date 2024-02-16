package com.wondoo.memberservice.diary.controller;

import com.wondoo.memberservice.diary.data.response.DiarySaveResponse;
import com.wondoo.memberservice.diary.service.DiaryLoadService;
import com.wondoo.memberservice.diary.service.DiarySaveService;
import com.wondoo.memberservice.global.annotation.RestWondooController;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;

@RestWondooController
@RequiredArgsConstructor
public class DiaryController {

    private final DiarySaveService diarySaveService;
    private final DiaryLoadService diaryLoadService;

    @GetMapping("/member/diary/{member_id}")
    public ResponseEntity<Page<DiarySaveResponse>> diaryLoadAll(
            @PathVariable("member_id") Long memberId,
            Pageable pageable
    ) throws IOException {

        return ResponseEntity.ok(diaryLoadService.diaryLoadAll(memberId, pageable));
    }

    @PostMapping("/auth/member/diary")
    public ResponseEntity<DiarySaveResponse> diarySave(
            @RequestHeader("member_id") Long memberId
    ) throws IOException {

        return ResponseEntity.ok(diarySaveService.diarySave(memberId, LocalDate.now()));
    }

    @DeleteMapping("/auth/member/diary")
    public ResponseEntity<String> diaryDelete(
            @RequestHeader("member_id") Long memberId,
            @RequestParam("diary_id") String diaryId
    ) throws IOException {

        diarySaveService.diaryDelete(memberId, diaryId);
        return ResponseEntity.ok("success");
    }
}
