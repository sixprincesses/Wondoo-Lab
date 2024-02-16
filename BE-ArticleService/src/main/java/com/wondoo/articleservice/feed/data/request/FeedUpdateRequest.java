package com.wondoo.articleservice.feed.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wondoo.articleservice.feed.domain.TimeDuration;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class FeedUpdateRequest {
    @JsonProperty("feed_id")
    private Long feedId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("content")
    private String content;
    @JsonProperty("time_logs")
    private List<TimeDuration> timeDurationList;
    @JsonProperty("keywords")
    private List<String> keyword;
}
