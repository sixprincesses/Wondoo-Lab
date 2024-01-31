package com.wondoo.memberservice.follow.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wondoo.memberservice.follow.data.query.FollowsResponse;
import com.wondoo.memberservice.follow.service.FollowLoadService;
import com.wondoo.memberservice.follow.service.FollowSaveService;
import com.wondoo.memberservice.global.annotation.RestWondooController;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestWondooController
@RequiredArgsConstructor
public class FollowController {

    private final FollowSaveService followSaveService;
    private final FollowLoadService followLoadService;

    @PostMapping("/member/{member_id}/follow")
    public ResponseEntity<String> memberFollow(
            @PathVariable("member_id") Long memberId,
            @RequestHeader("social_id") Long socialId
    ) throws JsonProcessingException {

        followSaveService.memberFollow(memberId, socialId);
        return ResponseEntity.ok("follow success");
    }

    @DeleteMapping("/member/{member_id}/follow")
    public ResponseEntity<String> memberUnfollow(
            @PathVariable("member_id") Long memberId,
            @RequestHeader("social_id") Long socialId
    ) {

        followSaveService.memberUnfollow(memberId, socialId);
        return ResponseEntity.ok("unfollow success");
    }

    @GetMapping("/member/{member_id}/followers")
    public ResponseEntity<Page<FollowsResponse>> memberFollowers(
            @PathVariable("member_id") Long memberId,
            Pageable pageable
    ) {

        return ResponseEntity.ok(followLoadService.followersLoad(memberId, pageable));
    }

    @GetMapping("/member/{member_id}/followings")
    public ResponseEntity<Page<FollowsResponse>> memberFollowings(
            @PathVariable("member_id") Long memberId,
            Pageable pageable
    ) {

        return ResponseEntity.ok(followLoadService.followingsLoad(memberId, pageable));
    }
}
