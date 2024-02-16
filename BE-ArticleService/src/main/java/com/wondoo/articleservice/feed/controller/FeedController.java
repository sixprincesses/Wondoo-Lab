package com.wondoo.articleservice.feed.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wondoo.articleservice._global.annotation.RestWondooController;
import com.wondoo.articleservice.feed.data.request.FeedCreateRequest;
import com.wondoo.articleservice.feed.data.request.FeedUpdateRequest;
import com.wondoo.articleservice.feed.data.response.FeedListResponse;
import com.wondoo.articleservice.feed.data.response.FeedResponse;
import com.wondoo.articleservice.feed.data.response.FeedStatisticLoadResponse;
import com.wondoo.articleservice.feed.data.response.FindLastFeedResponse;
import com.wondoo.articleservice.feed.service.FeedService;
import com.wondoo.articleservice.tempfeed.service.TempFeedSaveService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestWondooController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Slf4j
public class FeedController {
    private final FeedService feedService;
    private final TempFeedSaveService tempFeedSaveService;

    @GetMapping("/feed")
    public ResponseEntity<FeedResponse> findFeed(
            @RequestParam(value = "member_id", required = false) Long memberId,
            @RequestParam(value = "feed_id") Long feedId) {
        FeedResponse feedResponse = feedService.findFeed(memberId, feedId);
        // ApiResponse<FeedResponse> response = new ApiResponse<>(StatusCode.SUCCESS_SELECT, feedResponse);
        return ResponseEntity.status(HttpStatus.OK).body(feedResponse);
    }

    @GetMapping("/feeds")
    public ResponseEntity<FeedListResponse> findRecentFeedList(
            @RequestParam(value = "member_id", required = false) Long memberId,
            @RequestParam(value = "feed_id", required = false) Long feedId) {
        FeedListResponse feedListResponse = feedService.findRecentFeedList(memberId, feedId);
        //ApiResponse<FeedsResponse> response = new ApiResponse<>(StatusCode.SUCCESS_SELECT, feedsResponse);
        return ResponseEntity.status(HttpStatus.OK).body(feedListResponse);
    }

    @GetMapping("/auth/feeds/member")
    public ResponseEntity<FeedListResponse> findMemberFeedList(
            @RequestHeader(value = "member_id") Long authMemberId,
            @RequestParam(value = "member_id") Long memberId,
            @RequestParam(value = "feed_id", required = false) Long feedId) {
        FeedListResponse feedListResponse = feedService.findMemberFeedList(authMemberId, memberId, feedId);
        return ResponseEntity.status(HttpStatus.OK).body(feedListResponse);
    }

    @GetMapping("/feeds/keyword")
    public ResponseEntity<FeedListResponse> findKeywordFeedList(
            @RequestParam(value = "member_id", required = false) Long memberId,
            @RequestParam(value = "keyword") String keywordString,
            @RequestParam(value = "feed_id", required = false) Long feedId) {
        FeedListResponse feedListResponse = feedService.findKeywordFeedList(memberId, keywordString, feedId);
        return ResponseEntity.status(HttpStatus.OK).body(feedListResponse);
    }

    @GetMapping("/auth/feed/last")
    public ResponseEntity<FindLastFeedResponse> findLastFeed(
            @RequestHeader(value = "member_id") Long memberId
    ) {
        FindLastFeedResponse response = feedService.findLastFeed(memberId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/auth/feed")
    public ResponseEntity<Void> createFeed(
            @RequestHeader(value = "member_id") Long memberId,
            @RequestBody FeedCreateRequest request) throws JsonProcessingException {
        log.info("Create Feed memberId : [{}]", memberId);
        feedService.createFeed(memberId, request);
        tempFeedSaveService.tempFeedDelete(memberId);
        //ApiResponse<Void> response = new ApiResponse<>(StatusCode.SUCCESS_INSERT, null);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PutMapping("/auth/feed")
    public ResponseEntity<Void> updateFeed(
            @RequestHeader(value = "member_id") Long memberId,
            @RequestBody FeedUpdateRequest request) {
        feedService.updateFeed(memberId, request);
        // ApiResponse<Void> response = new ApiResponse<>(StatusCode.SUCCESS_UPDATE, null);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @DeleteMapping("/auth/feed")
    public ResponseEntity<Void> deleteFeed(
            @RequestHeader(value = "member_id") Long memberId,
            @RequestParam(value = "feed_id") Long feedId) throws JsonProcessingException {
        feedService.deleteFeed(memberId, feedId);
        // ApiResponse<Void> response = new ApiResponse<>(StatusCode.SUCCESS_DELETE, null);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/feed/statistic/{member_id}")
    public ResponseEntity<FeedStatisticLoadResponse> feedStatisticLoad(
            @PathVariable("member_id") Long memberId
    ) {
        return ResponseEntity.ok(feedService.feedStatisticLoad(memberId));
    }
}
