package com.wondoo.articleservice.match.service;

import com.wondoo.articleservice.feed.domain.Feed;
import com.wondoo.articleservice.feed.domain.FeedInfo;
import com.wondoo.articleservice.feed.repository.FeedInfoRepository;
import com.wondoo.articleservice.feed.repository.FeedRepository;
import com.wondoo.articleservice.feed.service.FeedService;
import com.wondoo.articleservice.keyword.data.response.KeywordCountListResponse;
import com.wondoo.articleservice.keyword.data.response.KeywordCountResponse;
import com.wondoo.articleservice.keyword.service.KeywordService;
import com.wondoo.articleservice.match.data.response.MatchFeedResponse;
import com.wondoo.articleservice.match.data.response.MatchMemberResponse;
import com.wondoo.articleservice.match.data.response.TimeFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final FeedService feedService;
    private final KeywordService keywordService;
    private final FeedRepository feedRepository;
    private final FeedInfoRepository feedInfoRepository;

    public MatchMemberResponse matchMember(Long memberId1, Long memberId2) {
        KeywordCountListResponse keywordCountList1 = keywordService.findKeywordCountWithZero(memberId1);
        KeywordCountListResponse keywordCountList2 = keywordService.findKeywordCountWithZero(memberId2);
        if (keywordCountList1.getKeywordCountList().size() != keywordCountList2.getKeywordCountList().size()) {
            throw new RuntimeException("키워드 수 불일치 "
                    + keywordCountList1.getKeywordCountList().size()
                    + " "
                    + keywordCountList2.getKeywordCountList().size());
        }
        List<String> labelList = new ArrayList<>();
        List<Long> dataList1 = new ArrayList<>();
        List<Long> dataList2 = new ArrayList<>();
        int size = keywordCountList1.getKeywordCountList().size();
        Long AA = 0L, BB = 0L, AB = 0L;
        for (int i = 0; i < size; i++) {
            Long A = keywordCountList1.getKeywordCountList().get(i).getCount();
            Long B = keywordCountList2.getKeywordCountList().get(i).getCount();
            AA += A * A;
            BB += B * B;
            AB += A * B;
            if (A == 0 && B == 0) {
                continue;
            }
            String name = keywordCountList1.getKeywordCountList().get(i).getName();
            labelList.add(name);
            dataList1.add(keywordCountList1.getKeywordCountList().get(i).getCount());
            dataList2.add(keywordCountList2.getKeywordCountList().get(i).getCount());
        }
        double similarity = Math.sqrt(AA) == 0 || Math.sqrt(BB) == 0
                ? 0
                : AB / Math.sqrt(AA) / Math.sqrt(BB) * 100;

        Long timeSum1 = feedService.findTimeSum(memberId1);
        Long timeSum2 = feedService.findTimeSum(memberId2);

        boolean moreThan = timeSum1 <= timeSum2;
        Long timeSumGap = Math.abs(timeSum1 - timeSum2);
        TimeFormat studyTime1 = TimeFormat.builder()
                .day(timeSum1 / 86400).hour(timeSum1 % 86400 / 3600)
                .minute(timeSum1 % 3600 / 60).second(timeSum1 % 60)
                .build();
        TimeFormat studyTime2 = TimeFormat.builder()
                .day(timeSum2 / 86400).hour(timeSum2 % 86400 / 3600)
                .minute(timeSum2 % 3600 / 60).second(timeSum2 % 60)
                .build();
        TimeFormat studyTimeGap = TimeFormat.builder()
                .day(timeSumGap / 86400).hour(timeSumGap % 86400 / 3600)
                .minute(timeSumGap % 3600 / 60).second(timeSumGap % 60)
                .build();
        return MatchMemberResponse.builder()
                .similarity(similarity)
                .labelList(labelList)
                .dataList1(dataList1)
                .dataList2(dataList2)
                .studyTime1(studyTime1)
                .studyTime2(studyTime2)
                .studyTimeGap(studyTimeGap)
                .moreThan(moreThan)
                .build();
    }

    public MatchFeedResponse matchFeed(Long memberId, Long feedId) {
        KeywordCountListResponse keywordCountList = keywordService.findKeywordCount(memberId);
        HashSet<String> memberKeywordList = new HashSet<>();
        for (KeywordCountResponse response : keywordCountList.getKeywordCountList()) {
            memberKeywordList.add(response.getName());
        }
        FeedInfo feedInfo = feedInfoRepository.findById(feedId)
                .orElseThrow(() -> new RuntimeException("피드 정보 조회 실패"));
        Feed feed = feedRepository.findByReference(feedInfo.getReference())
                .orElseThrow(() -> new RuntimeException("피드 조회 실패"));
        int intersection = 0;
        for(String keyword: feed.getKeyword()){
            if(memberKeywordList.contains(keyword)){
                intersection++;
            }
        }
        double similarity = feed.getKeyword().isEmpty()
                ? 0
                : ((double) intersection / feed.getKeyword().size());
        return MatchFeedResponse.builder()
                .similarity(similarity)
                .build();
    }
}
