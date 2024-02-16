package com.wondoo.articleservice.feed.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wondoo.articleservice.feed.domain.TimeDuration;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Builder
@Getter
public class FeedCreateRequest {
    @JsonProperty("title")
    private String title;
    @JsonProperty("content")
    private String content;
    @JsonProperty("time_logs")
    private List<TimeDuration> timeDurationList;
    @JsonProperty("keywords")
    private List<String> keyword;
}
