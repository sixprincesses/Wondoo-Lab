package com.wondoo.memberservice.planner.controller;

import com.wondoo.memberservice.global.annotation.RestWondooController;
import com.wondoo.memberservice.planner.data.request.PlannerSaveAllRequest;
import com.wondoo.memberservice.planner.data.request.TargetTimeRequest;
import com.wondoo.memberservice.planner.data.response.PlannerLoadAllResponse;
import com.wondoo.memberservice.planner.service.PlannerLoadService;
import com.wondoo.memberservice.planner.service.PlannerSaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestWondooController
@RequiredArgsConstructor
public class PlannerController {

    private final PlannerSaveService plannerSaveService;
    private final PlannerLoadService plannerLoadService;

    @PostMapping("/auth/member/targetTime")
    public ResponseEntity<String> targetTimeSave(
            @RequestHeader("member_id") Long memberId,
            @RequestBody @Valid TargetTimeRequest targetTimeRequest
    ) {

        plannerSaveService.targetTimeSave(memberId, targetTimeRequest);
        return ResponseEntity.ok("target time save success");
    }

    @GetMapping("/member/targetTime/{member_id}")
    public ResponseEntity<TargetTimeRequest> targetTimeLoad(
            @PathVariable("member_id") Long memberId
    ) {

        return ResponseEntity.ok(plannerLoadService.targetTimeLoad(memberId));
    }

    @GetMapping("/member/planner/{member_id}")
    public ResponseEntity<PlannerLoadAllResponse> plannerLoad(
            @PathVariable("member_id") Long memberId,
            @RequestParam("week") Long week
    ) {

        return ResponseEntity.ok(plannerLoadService.plannerLoadAll(memberId, week));
    }

    @PostMapping("/auth/member/planner")
    public ResponseEntity<String> plannerSave(
            @RequestHeader("member_id") Long memberId,
            @RequestBody @Valid PlannerSaveAllRequest plannerSaveAllRequest
    ) {

        plannerSaveService.plannerSaveAll(memberId, plannerSaveAllRequest);
        return ResponseEntity.ok("planner save success");
    }
}
