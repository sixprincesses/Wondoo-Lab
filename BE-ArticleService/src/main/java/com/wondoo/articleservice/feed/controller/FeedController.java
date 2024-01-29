package com.wondoo.articleservice.feed.controller;

import com.wondoo.articleservice.feed.data.request.RequestFeed;
import com.wondoo.articleservice.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@CrossOrigin("*")
@RequestMapping("/feed-service")
@RequiredArgsConstructor
@Slf4j
public class FeedController {
    private final FeedService feedService;

    @GetMapping("/feed/{feed_id}")
    public ResponseEntity<?> getRandomFeedList(@RequestParam(name = "feed_id") String feedId) {
        feedService.getFeeds(feedId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/feed")
    public ResponseEntity<?> createFeed(@RequestHeader("member_id") Long memberId, @RequestBody RequestFeed requestDto) {
        feedService.createFeed(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/feed")
    public ResponseEntity<?> updateFeed(@RequestHeader("member_id") Long memberId, @RequestBody RequestFeed requestDto) {
        feedService.updateFeed(requestDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/feed/{feed_id}")
    public ResponseEntity<?> deleteFeed(@RequestHeader("member_id") Long memberId, @RequestParam(name = "feed_id") String feedId) {
        feedService.deleteFeed(feedId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
