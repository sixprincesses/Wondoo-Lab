package com.wondoo.articleservice.match.controller;

import com.wondoo.articleservice._global.annotation.RestWondooController;
import com.wondoo.articleservice.match.data.response.MatchFeedResponse;
import com.wondoo.articleservice.match.data.response.MatchMemberResponse;
import com.wondoo.articleservice.match.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@RestWondooController
@RequiredArgsConstructor
public class MatchController {
    private final MatchService matchService;
    @GetMapping("/auth/match/member")
    public ResponseEntity<MatchMemberResponse> matchMember(
            @RequestParam(value = "member_id1") Long memberId1,
            @RequestParam(value = "member_id2") Long memberId2) {
        return ResponseEntity.ok(matchService.matchMember(memberId1, memberId2));
    }

    @GetMapping("/auth/match/feed")
    public ResponseEntity<MatchFeedResponse> matchFeed(
            @RequestHeader(value = "member_id") Long memberId,
            @RequestParam(value = "feed_id") Long feedId) {
        return ResponseEntity.ok(matchService.matchFeed(memberId, feedId));
    }
}
