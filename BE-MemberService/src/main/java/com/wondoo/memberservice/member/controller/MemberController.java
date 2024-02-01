package com.wondoo.memberservice.member.controller;

import com.wondoo.memberservice.global.annotation.RestWondooController;
import com.wondoo.memberservice.member.data.request.MemberUpdateRequest;
import com.wondoo.memberservice.member.data.response.BetweenServerResponse;
import com.wondoo.memberservice.member.data.response.MemberDetailResponse;
import com.wondoo.memberservice.member.service.MemberLoadService;
import com.wondoo.memberservice.member.service.MemberSaveService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestWondooController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberLoadService memberLoadService;
    private final MemberSaveService memberSaveService;

    @GetMapping("/member/between-server/{member_id}")
    public ResponseEntity<BetweenServerResponse> betweenServerLoad(@PathVariable("member_id") Long memberId){

        return ResponseEntity.ok(memberLoadService.betweenServerLoad(memberId));
    }

    @GetMapping("/member/{member_id}")
    public ResponseEntity<MemberDetailResponse> memberLoad(@PathVariable("member_id") Long memberId) {

        return ResponseEntity.ok(memberLoadService.memberDetailLoad(memberId));
    }

    @PutMapping("/member/{member_id}")
    public ResponseEntity<String> memberUpdate(
            @PathVariable("member_id") Long memberId,
            @RequestHeader("social_id") Long socialId,
            @RequestPart("json") @Valid MemberUpdateRequest memberUpdateRequest,
            @RequestPart("image_id") MultipartFile imageId
    ) throws IOException {

        memberSaveService.memberUpdate(memberId, socialId, memberUpdateRequest, imageId);
        return ResponseEntity.ok("update success");
    }
}
