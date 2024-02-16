package com.wondoo.articleservice.feed.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wondoo.articleservice.feed.domain.Feed;
import com.wondoo.articleservice.feed.domain.FeedInfo;
import com.wondoo.articleservice.feed.domain.TimeDuration;
import com.wondoo.articleservice.memberinfo.data.response.MemberInfoResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class FeedResponse {
    @JsonProperty("feed_id")
    private Long feedId;
    @JsonProperty("member")
    private MemberInfoResponse member;
    @JsonProperty("title")
    private String title;
    @JsonProperty("content")
    private String content;
    @JsonProperty("time_logs")
    private List<TimeDuration> timeDurationList;
    @JsonProperty("total_time")
    private Long totalTime;
    @JsonProperty("created_time")
    private LocalDateTime createdTime;
    @JsonProperty("updated_time")
    private LocalDateTime updatedTime;
    @JsonProperty("keywords")
    private List<String> keywords;
    @JsonProperty("is_liked")
    private boolean isLiked;
    @JsonProperty("is_bookmarked")
    private boolean isBookmarked;

    public static FeedResponse of(MemberInfoResponse memberInfoResponse,
                                  FeedInfo feedInfo,
                                  Feed feed,
                                  boolean isLiked,
                                  boolean isBookmarked) {
        return FeedResponse.builder()
                .member(memberInfoResponse)
                .feedId(feedInfo.getId())
                .title(feed.getTitle())
                .content(feed.getContent())
                .timeDurationList(feed.getTimeDurationList())
                .totalTime(feed.getTotalTime())
                .createdTime(feed.getCreatedTime())
                .updatedTime(feed.getUpdatedTime())
                .keywords(feed.getKeyword())
                .isLiked(isLiked)
                .isBookmarked(isBookmarked)
                .build();
    }
}
