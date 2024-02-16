package com.wondoo.articleservice.keyword.controller;

import com.wondoo.articleservice._global.annotation.RestWondooController;
import com.wondoo.articleservice.keyword.data.response.KeywordCountListResponse;
import com.wondoo.articleservice.keyword.data.response.KeywordRankListResponse;
import com.wondoo.articleservice.keyword.data.response.KeywordSearchResponse;
import com.wondoo.articleservice.keyword.service.KeywordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestWondooController
@RequiredArgsConstructor
public class KeywordController {
    private final KeywordService keywordService;

    @GetMapping("/keyword/member")
    public ResponseEntity<KeywordCountListResponse> findKeywordCount(
            @RequestParam(value = "member_id") Long memberId
    ) {
        return ResponseEntity.ok(keywordService.findKeywordCount(memberId));
    }

    @GetMapping("/keyword/rank")
    public ResponseEntity<KeywordRankListResponse> findKeywordRank() {
        return ResponseEntity.ok(keywordService.findKeywordRank());
    }

    @GetMapping("/keyword/search")
    public ResponseEntity<KeywordSearchResponse> searchKeyword(
            @RequestParam(value = "keyword") String keywordString) {
        return ResponseEntity.ok(keywordService.findKeywordSearchList(keywordString));
    }
}
