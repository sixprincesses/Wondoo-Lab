package com.wondoo.articleservice.like.controller;

import com.wondoo.articleservice._global.annotation.RestWondooController;
import com.wondoo.articleservice.like.data.request.LikeDeleteRequest;
import com.wondoo.articleservice.like.data.request.LikeSaveRequest;
import com.wondoo.articleservice.like.data.request.LikesRequest;
import com.wondoo.articleservice.like.data.response.LikeDeleteResponse;
import com.wondoo.articleservice.like.data.response.LikeSaveResponse;
import com.wondoo.articleservice.like.data.response.LikesResponse;
import com.wondoo.articleservice.like.service.LikeLoadService;
import com.wondoo.articleservice.like.service.LikeModifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestWondooController
public class LikeController {

    private final LikeModifyService likeModifyService;
    private final LikeLoadService likeLoadService;

    @PostMapping("/auth/like")
    public ResponseEntity<LikeSaveResponse> save(
            @RequestHeader("member_id") Long memberId,
            @RequestBody LikeSaveRequest request
    ) {
        LikeSaveResponse response = likeModifyService.save(memberId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/auth/like")
    public ResponseEntity<LikesResponse> getLikes(
            @RequestHeader("member_id") Long memberId,
            @RequestBody LikesRequest request
    ) {
        LikesResponse response = likeLoadService.findLikes(memberId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/auth/like")
    public ResponseEntity<LikeDeleteResponse> delete(
            @RequestHeader("member_id") Long memberId,
            @RequestBody LikeDeleteRequest request
    ) {
        LikeDeleteResponse response = likeModifyService.delete(memberId, request);
        return ResponseEntity.ok(response);
    }
}
