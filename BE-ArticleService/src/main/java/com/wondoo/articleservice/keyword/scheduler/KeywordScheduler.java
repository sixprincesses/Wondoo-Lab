package com.wondoo.articleservice.keyword.scheduler;

import com.wondoo.articleservice.keyword.service.KeywordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KeywordScheduler {
    private final KeywordService keywordService;

    @Scheduled(cron = "0 0/5 * * * *")
    public void updateKeywordRankList(){
        log.info("##### UpdateKeywordRank --- START");
        keywordService.updateKeywordRankList();
        log.info("##### UpdateKeywordRank --- END");
    }
}
