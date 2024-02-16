package com.wondoo.articleservice.tempfeed.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wondoo.articleservice.feed.domain.TimeDuration;
import lombok.Builder;

import java.util.List;

@Builder
public record TempFeedLoadResponse(
        @JsonProperty("member_id")
        Long memberId,
        @JsonProperty("title")
        String title,
        @JsonProperty("content")
        String content,
        @JsonProperty("time_logs")
        List<TimeDuration> timeLogs,
        @JsonProperty("total_time")
        Long totalTime,
        @JsonProperty("keywords")
        List<String> keywords
) {
}
