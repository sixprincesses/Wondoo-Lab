package com.wondoo.memberservice.follow.controller;

import com.wondoo.memberservice.follow.service.FollowSaveService;
import com.wondoo.memberservice.global.annotation.RestWondooController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@RestWondooController
@RequiredArgsConstructor
public class FollowController {

    private final FollowSaveService followSaveService;

    @PostMapping("member/{member_id}/follow")
    public ResponseEntity<String> memberFollow(
            @PathVariable("member_id") Long memberId,
            @RequestHeader("social_id") Long socialId
    ) {

        followSaveService.memberFollow(memberId, socialId);
        return ResponseEntity.ok("follow success");
    }
}
