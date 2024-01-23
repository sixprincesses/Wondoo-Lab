package com.wondoo.memberservice.member.controller;

import com.wondoo.memberservice.member.data.response.MemberDetailResponse;
import com.wondoo.memberservice.member.service.MemberLoadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberLoadService memberLoadService;

    @GetMapping("member/{member_id}")
    public ResponseEntity<MemberDetailResponse> memberLoad(@PathVariable("member_id") Long memberId) {

        return ResponseEntity.ok(memberLoadService.memberLoad(memberId));
    }
}
