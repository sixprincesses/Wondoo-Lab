package com.wondoo.memberservice.member.controller;

import com.wondoo.memberservice.global.annotation.RestWondooController;
import com.wondoo.memberservice.member.data.request.MemberUpdateRequest;
import com.wondoo.memberservice.member.data.response.BetweenServerResponse;
import com.wondoo.memberservice.member.data.response.MemberDetailResponse;
import com.wondoo.memberservice.member.service.MemberLoadService;
import com.wondoo.memberservice.member.service.MemberSaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestWondooController
@RequiredArgsConstructor
public class MemberController {

    private final MemberLoadService memberLoadService;
    private final MemberSaveService memberSaveService;

    @GetMapping("member/between-server/{member_id}")
    public ResponseEntity<BetweenServerResponse> betweenServerLoad(@PathVariable("member_id") Long memberId){

        return ResponseEntity.ok(memberLoadService.betweenServerLoad(memberId));
    }

    @GetMapping("member/{member_id}")
    public ResponseEntity<MemberDetailResponse> memberLoad(@PathVariable("member_id") Long memberId) {

        return ResponseEntity.ok(memberLoadService.memberDetailLoad(memberId));
    }

    @PutMapping("member/{member_id}")
    public ResponseEntity<String> memberUpdate(
            @PathVariable("member_id") Long memberId,
            @RequestHeader("social_id") Long socialId,
            @RequestBody @Valid MemberUpdateRequest memberUpdateRequest
    ) {

        memberSaveService.memberUpdate(memberId, socialId, memberUpdateRequest);
        return ResponseEntity.ok("update success");
    }
}
