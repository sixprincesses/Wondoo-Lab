package com.wondoo.articleservice.tempfeed.service;

import com.wondoo.articleservice.feed.domain.TimeDuration;
import com.wondoo.articleservice.tempfeed.data.request.TempFeedSaveRequest;
import com.wondoo.articleservice.tempfeed.data.response.TempFeedLoadResponse;
import com.wondoo.articleservice.tempfeed.domain.TempFeed;
import com.wondoo.articleservice.tempfeed.exception.TempFeedErrorCode;
import com.wondoo.articleservice.tempfeed.exception.TempFeedException;
import com.wondoo.articleservice.tempfeed.repository.TempFeedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.ServerException;
import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class TempFeedService implements TempFeedSaveService, TempFeedLoadService {

    private final TempFeedRepository tempFeedRepository;

    /**
     * 피드 임시 저장
     *
     * @param memberId            요청자 member_id
     * @param tempFeedSaveRequest 임시 저장 요청 내용
     */
    @Transactional
    @Override
    public void tempFeedSave(Long memberId, TempFeedSaveRequest tempFeedSaveRequest) {

        if (tempFeedSaveRequest.timeLogs() == null) {
            throw new TempFeedException(TempFeedErrorCode.TIME_LOG_ERROR);
        }
        Optional<TempFeed> tempFeedCheck = tempFeedRepository.findById(memberId);
        if (tempFeedCheck.isPresent()) {
            TempFeed tempFeed = tempFeedCheck.get();
            long totalTime = calculateTotalTime(tempFeedSaveRequest);

            tempFeed.updateTempFeed(
                    tempFeedSaveRequest.content(),
                    tempFeedSaveRequest.timeLogs(),
                    totalTime
            );
            tempFeedRepository.save(tempFeed);
            return;
        }

        long totalTime = calculateTotalTime(tempFeedSaveRequest);
        TempFeed tempFeed = TempFeed.builder()
                .memberId(memberId)
                .content(tempFeedSaveRequest.content())
                .timeLogs(tempFeedSaveRequest.timeLogs())
                .totalTime(totalTime)
                .build();
        tempFeedRepository.save(tempFeed);
    }

    /**
     * 피드 저장 요청 시 임시 저장 피드 삭제
     *
     * @param memberId 요청자 member_id
     */
    @Transactional
    @Override
    public void tempFeedDelete(Long memberId) {

        tempFeedRepository.findById(memberId).
                ifPresent(tempFeedRepository::delete);
    }

    /**
     * 임시 저장 피드 조회
     *
     * @param memberId 요청자 member_id
     * @return 임시 저장 피드
     */
    @Override
    public TempFeedLoadResponse tempFeedLoad(Long memberId) {

        Optional<TempFeed> tempFeedCheck = tempFeedRepository.findById(memberId);
        if (tempFeedCheck.isPresent()) {
            TempFeed tempFeed = tempFeedCheck.get();
            return TempFeedLoadResponse.builder()
                    .memberId(tempFeed.getMemberId())
                    .content(tempFeed.getContent())
                    .timeLogs(tempFeed.getTimeLogs())
                    .totalTime(tempFeed.getTotalTime())
                    .build();
        }
        return null;
    }

    private long calculateTotalTime(TempFeedSaveRequest tempFeedSaveRequest) {
        long totalTime = 0;
        for (TimeDuration timeLog : tempFeedSaveRequest.timeLogs()) {
            Duration duration = Duration.between(
                    timeLog.getStartTime(), timeLog.getEndTime()
            );
            totalTime += duration.getSeconds();
        }
        return totalTime;
    }
}
