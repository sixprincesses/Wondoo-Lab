package com.wondoo.articleservice.feed.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wondoo.articleservice._global.utils.KafkaProducer;
import com.wondoo.articleservice._global.utils.UUIDFactory;
import com.wondoo.articleservice.bookmark.repository.BookmarkRepository;
import com.wondoo.articleservice.bookmark.utils.BookmarkServiceUtils;
import com.wondoo.articleservice.feed.data.message.ArticleUpdateMessage;
import com.wondoo.articleservice.feed.data.request.FeedCreateRequest;
import com.wondoo.articleservice.feed.data.request.FeedUpdateRequest;
import com.wondoo.articleservice.feed.data.response.*;
import com.wondoo.articleservice.feed.domain.Feed;
import com.wondoo.articleservice.feed.domain.FeedInfo;
import com.wondoo.articleservice.feed.domain.TimeDuration;
import com.wondoo.articleservice.feed.repository.FeedInfoRepository;
import com.wondoo.articleservice.feed.repository.FeedRepository;
import com.wondoo.articleservice.keyword.domain.Keyword;
import com.wondoo.articleservice.keyword.domain.KeywordInfo;
import com.wondoo.articleservice.keyword.repository.KeywordInfoRepository;
import com.wondoo.articleservice.keyword.repository.KeywordRepository;
import com.wondoo.articleservice.keyword.service.KeywordService;
import com.wondoo.articleservice.like.repository.LikeRepository;
import com.wondoo.articleservice.like.utils.LikeServiceUtils;
import com.wondoo.articleservice.memberinfo.data.response.MemberInfoResponse;
import com.wondoo.articleservice.memberinfo.domain.MemberInfo;
import com.wondoo.articleservice.memberinfo.repository.MemberInfoRepository;
import com.wondoo.articleservice.memberinfo.utils.MemberInfoServiceUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class FeedService {
    private final int SIZE = 10;
    private final UUIDFactory uuidFactory;
    private final FeedRepository feedRepository;
    private final KeywordRepository keywordRepository;
    private final KeywordInfoRepository keywordInfoRepository;
    private final FeedInfoRepository feedInfoRepository;
    private final MemberInfoRepository memberInfoRepository;
    private final LikeRepository likeRepository;
    private final BookmarkRepository bookmarkRepository;
    private final KeywordService keywordService;
    private final KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper;

    public FeedResponse findFeed(Long memberId, Long feedId) {
        FeedInfo feedInfo = feedInfoRepository.findById(feedId)
                .orElseThrow(() -> new NoSuchElementException("피드 정보 조회 실패"));
        Feed feed = feedRepository.findByReference(feedInfo.getReference())
                .orElseThrow(() -> new NoSuchElementException("피드 조회 실패"));
        MemberInfo memberInfo = memberInfoRepository.findById(feed.getMemberId())
                .orElseThrow(() -> new NoSuchElementException("피드 회원 정보 조회 실패"));
        MemberInfoResponse memberInfoResponse = MemberInfoResponse.of(feed, memberInfo);

        boolean isLiked = LikeServiceUtils
                .isLikedByMemberIdAndFeedId(likeRepository, memberId, feedId);
        boolean isBookmarked = BookmarkServiceUtils
                .isBookmarkedByMemberIdAndFeedId(bookmarkRepository, memberId, feedId);

        return FeedResponse.of(memberInfoResponse, feedInfo, feed, isLiked, isBookmarked);
    }

    public FeedListResponse findRecentFeedList(Long memberId, Long feedId) {
        long lastFeedId = Long.MAX_VALUE;
        FeedListResponse feedListResponse = new FeedListResponse();
        List<FeedInfo> feedInfoList = feedInfoRepository.findRecentFeedList(feedId, SIZE);
        for (FeedInfo feedInfo : feedInfoList) {
            Feed feed = feedRepository.findByReference(feedInfo.getReference())
                    .orElseThrow(() -> new RuntimeException("피드 조회 실패"));
            MemberInfo memberInfo = feedInfo.getMemberInfo();
            MemberInfoResponse memberInfoResponse = MemberInfoResponse.of(feed, memberInfo);
            boolean isLiked = LikeServiceUtils
                    .isLikedByMemberIdAndFeedId(likeRepository, memberId, feedInfo.getId());
            boolean isBookmarked = BookmarkServiceUtils
                    .isBookmarkedByMemberIdAndFeedId(bookmarkRepository, memberId, feedInfo.getId());
            FeedResponse feedResponse = FeedResponse.of(memberInfoResponse, feedInfo, feed, isLiked, isBookmarked);
            feedListResponse.getFeedsResponse().add(feedResponse);
            lastFeedId = Math.min(lastFeedId, feedInfo.getId());
        }
        lastFeedId = (lastFeedId == Long.MAX_VALUE ? -1 : lastFeedId);
        feedListResponse.updateLastFeedId(lastFeedId);
        return feedListResponse;
    }

    public FeedListResponse findMemberFeedList(Long authMemberId, Long memberId, Long feedId) {
        long lastFeedId = Long.MAX_VALUE;
        FeedListResponse feedListResponse = new FeedListResponse();
        List<FeedInfo> feedInfoList = feedInfoRepository.findMemberFeedList(feedId, memberId, SIZE);
        MemberInfo memberInfo = memberInfoRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("피드 회원 정보 조회 실패"));
        for (FeedInfo feedInfo : feedInfoList) {
            Feed feed = feedRepository.findByReference(feedInfo.getReference())
                    .orElseThrow(() -> new RuntimeException("피드 조회 실패"));
            MemberInfoResponse memberInfoResponse = MemberInfoResponse.of(feed, memberInfo);
            boolean isLiked = LikeServiceUtils
                    .isLikedByMemberIdAndFeedId(likeRepository, authMemberId, feedInfo.getId());
            boolean isBookmarked = BookmarkServiceUtils
                    .isBookmarkedByMemberIdAndFeedId(bookmarkRepository, authMemberId, feedInfo.getId());
            FeedResponse feedResponse = FeedResponse.of(memberInfoResponse, feedInfo, feed, isLiked, isBookmarked);
            feedListResponse.getFeedsResponse().add(feedResponse);
            lastFeedId = Math.min(lastFeedId, feedInfo.getId());
        }
        lastFeedId = (lastFeedId == Long.MAX_VALUE ? -1 : lastFeedId);
        feedListResponse.updateLastFeedId(lastFeedId);
        return feedListResponse;
    }

    public FeedListResponse findKeywordFeedList(Long memberId, String keywordString, Long feedId) {
        long lastFeedId = Long.MAX_VALUE;
        FeedListResponse feedListResponse = new FeedListResponse();
        Keyword keyword = keywordRepository.findByName(keywordString)
                .orElseThrow(() -> new RuntimeException("키워드 조회 실패"));
        List<KeywordInfo> keywordInfoList = keywordInfoRepository.findKeywordFeedList(feedId, keyword.getId(), SIZE);
        for (KeywordInfo keywordInfo : keywordInfoList) {
            FeedInfo feedInfo = feedInfoRepository.findById(keywordInfo.getFeedId())
                    .orElseThrow(() -> new RuntimeException("피드 정보 조회 실패"));
            MemberInfo memberInfo = feedInfo.getMemberInfo();
            Feed feed = feedRepository.findByReference(feedInfo.getReference())
                    .orElseThrow(() -> new RuntimeException("피드 조회 실패"));
            MemberInfoResponse memberInfoResponse = MemberInfoResponse.of(feed, memberInfo);
            boolean isLiked = LikeServiceUtils
                    .isLikedByMemberIdAndFeedId(likeRepository, memberId, keywordInfo.getFeedId());
            boolean isBookmarked = BookmarkServiceUtils
                    .isBookmarkedByMemberIdAndFeedId(bookmarkRepository, memberId, keywordInfo.getFeedId());
            FeedResponse feedResponse = FeedResponse.of(memberInfoResponse, feedInfo, feed, isLiked, isBookmarked);
            feedListResponse.getFeedsResponse().add(feedResponse);
            lastFeedId = Math.min(lastFeedId, feedInfo.getId());
        }
        lastFeedId = (lastFeedId == Long.MAX_VALUE ? -1 : lastFeedId);
        feedListResponse.updateLastFeedId(lastFeedId);
        return feedListResponse;
    }

    @Transactional
    public void createFeed(Long memberId, FeedCreateRequest request) throws JsonProcessingException {
        String reference = uuidFactory.generateUUIDWithoutParam();
        MemberInfo memberInfo = MemberInfoServiceUtils.findById(memberInfoRepository, memberId);
        FeedInfo feedInfo = feedInfoRepository.save(FeedInfo.builder()
                .memberInfo(memberInfo)
                .reference(reference)
                .title(request.getTitle())
                .build()
        );
        long totalTime = 0;
        for (TimeDuration timeDuration : request.getTimeDurationList()) {
            Duration duration = Duration.between(timeDuration.getStartTime(), timeDuration.getEndTime());
            totalTime += duration.getSeconds();
        }

        feedRepository.save(Feed.builder()
                .reference(reference)
                .memberId(memberId)
                .title(request.getTitle())
                .content(request.getContent())
                .timeDurationList(request.getTimeDurationList())
                .totalTime(totalTime)
                .keyword(request.getKeyword())
                .build());

        keywordService.createKeywordInfoList(memberId, feedInfo.getId(), request.getKeyword());

        kafkaProducer.sendArticleUpdateMessage(
                "SAVE",
                objectMapper.writeValueAsString(
                        ArticleUpdateMessage.builder()
                                .memberId(memberId)
                                .date(LocalDate.now())
                                .build()
                )
        );
    }

    @Transactional
    public void updateFeed(Long memberId, FeedUpdateRequest request) {
        FeedInfo feedInfo = feedInfoRepository.findById(request.getFeedId())
                .orElseThrow(() -> new RuntimeException("피드 정보 조회 실패"));
        Feed feed = feedRepository.findByReference(feedInfo.getReference())
                .orElseThrow(() -> new RuntimeException("피드 조회 실패"));
        if (!memberId.equals(feed.getMemberId())) {
            throw new RuntimeException("피드 회원 정보 불일치");
        }
        feed.updateFeed(request.getTitle(), request.getContent(), request.getKeyword());
        feedInfo.updateTitle(request.getTitle());
        keywordService.deleteKeywordInfo(feedInfo.getId());
        keywordService.createKeywordInfoList(memberId, feedInfo.getId(), request.getKeyword());
    }

    @Transactional
    public void deleteFeed(Long memberId, Long feedId) throws JsonProcessingException {
        FeedInfo feedInfo = feedInfoRepository.findById(feedId)
                .orElseThrow(() -> new RuntimeException("피드 정보 조회 실패"));
        Feed feed = feedRepository.findByReference(feedInfo.getReference())
                .orElseThrow(() -> new RuntimeException("피드 조회 실패"));
        if (!memberId.equals(feed.getMemberId())) {
            throw new RuntimeException("피드 회원 정보 불일치");
        }
        feedRepository.deleteByReference(feedInfo.getReference());
        feedInfoRepository.deleteById(feedId);
        keywordService.deleteKeywordInfo(feedInfo.getId());
        kafkaProducer.sendArticleUpdateMessage(
                "DELETE",
                objectMapper.writeValueAsString(
                        ArticleUpdateMessage.builder()
                                .memberId(memberId)
                                .date(LocalDate.now())
                                .build()
                )
        );
    }

    public FindLastFeedResponse findLastFeed(Long memberId) {
        FeedInfo findFeedInfo = feedInfoRepository.findLastFeedByMemberId(memberId)
                .orElseThrow(() -> new NoSuchElementException("마지막 피드가 존재하지 않습니다."));
        Feed findFeed = feedRepository.findByReference(findFeedInfo.getReference())
                .orElseThrow(() -> new NoSuchElementException("피드가 존재하지 않습니다."));
        return FindLastFeedResponse.of(findFeed, findFeedInfo);
    }

    public FeedStatisticLoadResponse feedStatisticLoad(Long memberId) {

        List<FeedStatisticDetailResponse> monthlyTotalTimes = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < 6; i++) {
            // 현재 월부터 6개월 전까지의 YearMonth 계산
            YearMonth targetMonth = YearMonth.from(now.minusMonths(i));

            // 해당 월의 시작일과 종료일 계산
            LocalDateTime startOfMonth = targetMonth.atDay(1).atStartOfDay();
            LocalDateTime endOfMonth = targetMonth.atEndOfMonth().atTime(23, 59, 59);

            // 해당 월의 총 공부 시간 계산
            Long monthlyTotalTime = 0L;
            List<Feed> feeds = feedRepository.findFeedByMemberIdAndCreatedTimeInCurrentMonth(
                    memberId, startOfMonth, endOfMonth
            );
            log.info("FEED: [{}]", feeds);
            for (Feed feed : feeds) {
                monthlyTotalTime += feed.getTotalTime();
            }
            log.info("TOTAL: [{}]", monthlyTotalTime);
            monthlyTotalTimes.add(
                    FeedStatisticDetailResponse.builder()
                            .yearMonth(targetMonth.toString())
                            .totalTime(monthlyTotalTime)
                            .build()
            );
        }

        log.info("RES: [{}]", monthlyTotalTimes);
        return FeedStatisticLoadResponse.builder()
                .feedStatistic(monthlyTotalTimes)
                .build();
    }

    public Long findTimeSum(Long memberId){
        List<FeedInfo> feedInfoList = feedInfoRepository.findAllByMemberInfo_Id(memberId);
        Long timeSum = 0L;
        for(FeedInfo feedInfo: feedInfoList){
            Feed feed = feedRepository.findByReference(feedInfo.getReference())
                    .orElseThrow(() -> new RuntimeException("피드 조회 실패"));
            timeSum += feed.getTotalTime();
        }
        return timeSum;
    }
}
