package com.wondoo.articleservice.feed.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wondoo.articleservice.feed.domain.Feed;
import com.wondoo.articleservice.feed.domain.FeedInfo;
import lombok.Builder;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;

@Builder
public record FindLastFeedResponse(
        @JsonProperty("feed_id")
        Long feed_id,

        @JsonProperty("feed_title")
        String title,

        @JsonProperty("date")
        LocalDateTime date
) {
    public static FindLastFeedResponse of(Feed feed, FeedInfo feedInfo) {
        return FindLastFeedResponse.builder()
                .feed_id(feedInfo.getId())
                .title(feedInfo.getTitle())
                .date(feed.getCreatedTime())
                .build();
    }
}
