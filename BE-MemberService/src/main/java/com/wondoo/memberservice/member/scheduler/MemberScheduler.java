package com.wondoo.memberservice.member.scheduler;

import com.wondoo.memberservice.diary.service.DiarySaveService;
import com.wondoo.memberservice.member.service.MemberSaveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberScheduler {

    private final MemberSaveService memberSaveService;
    private final DiarySaveService diarySaveService;

    @Scheduled(cron = "0 30 11 * * *")
    public void runMemberLevelReload() {
        log.info("runMemberLevelReload() ---> Start");
        memberSaveService.memberLevelReload();
        log.info("runMemberLevelReload() ---> End");
    }

    @Scheduled(cron = "0 30 11 * * FRI")
    public void runMemberRankingInit() {
        log.info("runMemberRankingInit() ---> Start");
        memberSaveService.memberRankingInit();
        log.info("runMemberRankingInit() ---> End");
    }

    @Scheduled(cron = "0 30 11 * * *")
    public void diarySaveAuto() {
        log.info("diaryAuto() ---> Start");
        diarySaveService.diarySaveAuto();
        log.info("diaryAuto() ---> End");
    }
}
