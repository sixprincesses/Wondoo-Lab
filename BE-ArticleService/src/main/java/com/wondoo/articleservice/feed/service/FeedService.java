package com.wondoo.articleservice.feed.service;

import com.wondoo.articleservice.feed.data.request.FeedRequest;
import com.wondoo.articleservice.feed.data.response.FeedResponse;
import com.wondoo.articleservice.feed.domain.Feed;
import com.wondoo.articleservice.feed.domain.FeedMember;
import com.wondoo.articleservice.feed.repository.FeedMemberRepository;
import com.wondoo.articleservice.feed.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class FeedService {
    private final FeedRepository feedRepository;
    private final FeedMemberRepository feedMemberRepository;

    public List<FeedResponse> getFeeds(String feedId) {
        Long id = feedMemberRepository.findByFeedId(feedId).getId();
        List<String> feedIds = feedMemberRepository.findByRecentFeedId(id);
        List<Feed> feeds = feedRepository.findByIdIn(feedIds);
        List<FeedResponse> responseFeeds = new ArrayList<>();
        for (Feed feed : feeds) {
            responseFeeds.add(FeedResponse.builder()
                    .feedId(feed.getId())
                    //.nickname()
                    .title(feed.getTitle())
                    .content(feed.getContent())
                    .timeLogs(feed.getTimeLogs())
                    .totalTime(feed.getTotalTime())
                    .createdTime(feed.getCreatedTime())
                    .updatedTime(feed.getUpdatedTime())
                    .build());
            // nickname 넣기
        }
        return responseFeeds;
    }

    @Transactional
    public void createFeed(FeedRequest requestDto) {
        String feedId = UUID.randomUUID().toString();

        FeedMember feedMember = FeedMember.builder()
                .feedId(feedId)
                .memberId(requestDto.getMemberId())
                .build();
        feedMemberRepository.save(feedMember);

        long totalTime = 0;
        for (List<LocalDateTime> timelog : requestDto.getTimeLogs()) {
            Duration duration = Duration.between(timelog.get(0), timelog.get(1));
            totalTime += duration.getSeconds();
        }
        Feed feed = Feed.builder()
                .id(feedId)
                .memberId(requestDto.getMemberId())
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .timeLogs(requestDto.getTimeLogs())
                .totalTime(totalTime)
                .build();
        feedRepository.save(feed);
    }

    @Transactional
    public void updateFeed(FeedRequest requestDto) {
        Feed feed = feedRepository.findById(requestDto.getFeedId())
                .orElseThrow(() -> new RuntimeException());
        feed.updateFeed(requestDto.getTitle(), requestDto.getContent());
    }

    @Transactional
    public void deleteFeed(String feedId) {
        feedRepository.deleteById(feedId);
    }
}
