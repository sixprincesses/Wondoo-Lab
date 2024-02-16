package com.wondoo.memberservice.member.controller;

import com.wondoo.memberservice.global.annotation.RestWondooController;
import com.wondoo.memberservice.member.data.request.MemberUpdateRequest;
import com.wondoo.memberservice.member.data.request.TokenUpdateRequest;
import com.wondoo.memberservice.member.data.response.*;
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

    @GetMapping("/auth/member/{member_id}")
    public ResponseEntity<MemberDetailResponse> memberLoad(
            @PathVariable("member_id") Long memberId,
            @RequestHeader("member_id") Long checkId
    ) {
        return ResponseEntity.ok(memberLoadService.memberDetailLoad(memberId, checkId));
    }

    @PutMapping("/auth/member")
    public ResponseEntity<MemberUpdateResponse> memberUpdate(
            @RequestHeader("member_id") Long memberId,
            @RequestBody @Valid MemberUpdateRequest memberUpdateRequest
    ) {

        return ResponseEntity.ok(memberSaveService.memberUpdate(memberId, memberUpdateRequest));
    }

    @GetMapping("/member")
    public ResponseEntity<MemberSearchResponse> memberSearch(
            @RequestParam("keyword") String keyword
    ) {
        return ResponseEntity.ok(memberLoadService.memberSearchLoad(keyword));
    }

    @GetMapping("/member/ranking")
    public ResponseEntity<MemberRankingResponse> memberRanking() {
        return ResponseEntity.ok(memberLoadService.memberRankingLoad());
    }

    @PostMapping("/auth/member/openai")
    public ResponseEntity<Void> updateOpenaiToken(
            @RequestBody TokenUpdateRequest request,
            @RequestHeader("member_id") Long memberId
    ) {
        memberSaveService.updateOpenaiToken(memberId, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/auth/member/github")
    public ResponseEntity<Void> updateGithubToken(
            @RequestBody TokenUpdateRequest request,
            @RequestHeader("member_id") Long memberId
    ) {
        memberSaveService.updateGithubToken(memberId, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/auth/member/image")
    public ResponseEntity<ImageResponse> updateImage(
            @RequestHeader("member_id") Long memberId,
            @RequestPart(value = "image_id") MultipartFile image
    ) throws IOException {

        return ResponseEntity.ok(memberSaveService.updateImage(memberId, image));
    }

    @PostMapping("/auth/member/cover")
    public ResponseEntity<ImageResponse> updateCoverImage(
            @RequestHeader("member_id") Long memberId,
            @RequestPart(value = "image_id") MultipartFile image
    ) throws IOException {

        return ResponseEntity.ok(memberSaveService.updateCoverImage(memberId, image));
    }
}
