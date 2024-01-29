package com.wondoo.articleservice.feed.service;

import com.wondoo.articleservice.feed.data.request.RequestFeed;
import com.wondoo.articleservice.feed.domain.Feed;
import com.wondoo.articleservice.feed.domain.FeedMember;
import com.wondoo.articleservice.feed.repository.FeedMemberRepository;
import com.wondoo.articleservice.feed.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FeedService {
    private final FeedRepository feedRepository;
    private final FeedMemberRepository feedMemberRepository;

    public void getFeeds(String feedId) {
        // FeedMember에서 index별로 가져오기 g
    }

    @Transactional
    public void createFeed(RequestFeed requestDto) {
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
                .TimeLogs(requestDto.getTimeLogs())
                .totalTime(totalTime)
                .build();
        feedRepository.save(feed);
    }

    @Transactional
    public void updateFeed(RequestFeed requestDto) {
        Optional<Feed> feed = feedRepository.findById(requestDto.getFeedId());
        if (feed.isPresent()) {
            feed.get().updateFeed(requestDto.getTitle(), requestDto.getContent());
            feedRepository.save(feed.get());
        }
    }

    @Transactional
    public void deleteFeed(String feedId) {
        feedRepository.deleteById(feedId);
    }
}
