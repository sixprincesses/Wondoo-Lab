package com.wondoo.articleservice.comment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wondoo.articleservice._global.annotation.RestWondooController;
import com.wondoo.articleservice._global.data.ApiResponse;

import com.wondoo.articleservice.comment.data.request.CommentCreateRequest;
import com.wondoo.articleservice.comment.data.request.CommentUpdateRequest;
import com.wondoo.articleservice.comment.data.response.CommentListResponse;
import com.wondoo.articleservice.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestWondooController
@RequiredArgsConstructor
@Slf4j
public class CommentController {
    private final CommentService commentService;
    @GetMapping("/comment")
    public ResponseEntity<CommentListResponse> findCommentList(
            @RequestParam(value = "feed_id", required = true) Long feedId){
        CommentListResponse commentList = commentService.findCommentList(feedId);
        //ApiResponse<CommentListResponse> response = new ApiResponse<>(StatusCode.SUCCESS_SELECT, commentList);
        return ResponseEntity.status(HttpStatus.OK).body(commentList);
    }
    @PostMapping("/auth/comment")
    public ResponseEntity<Void> createComment(
            @RequestHeader(value = "member_id", required = true) Long memberId,
            @RequestBody CommentCreateRequest request) throws JsonProcessingException {
        commentService.createComment(memberId, request);
        //ApiResponse<Void> response = new ApiResponse<>(StatusCode.SUCCESS_INSERT, null);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    @PutMapping("/auth/comment")
    public ResponseEntity<Void> updateComment(
            @RequestHeader(value = "member_id", required = true) Long memberId,
            @RequestBody CommentUpdateRequest request){
        commentService.updateComment(memberId, request);
        //ApiResponse<Void> response = new ApiResponse<>(StatusCode.SUCCESS_UPDATE, null);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    @DeleteMapping("/auth/comment")
    public ResponseEntity<Void> deleteComment(
            @RequestHeader(value = "member_id", required = true) Long memberId,
            @RequestParam(value = "comment_id", required = true) Long commentId){
        commentService.deleteComment(memberId, commentId);
        //ApiResponse<Void> response = new ApiResponse<>(StatusCode.SUCCESS_DELETE, null);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
