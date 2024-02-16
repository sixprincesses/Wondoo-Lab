package com.wondoo.memberservice.streak.controller;

import com.wondoo.memberservice.global.annotation.RestWondooController;
import com.wondoo.memberservice.streak.data.response.StreakResponse;
import com.wondoo.memberservice.streak.service.StreakLoadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@RestWondooController
public class StreakController {
    private final StreakLoadService streakLoadService;

    @GetMapping("/streak/{member_id}")
    public ResponseEntity<StreakResponse> getStreak(
            @PathVariable("member_id") Long memberId
    ) {
        StreakResponse response = streakLoadService.findMemberStreak(memberId);
        return ResponseEntity.ok(response);
    }
}
