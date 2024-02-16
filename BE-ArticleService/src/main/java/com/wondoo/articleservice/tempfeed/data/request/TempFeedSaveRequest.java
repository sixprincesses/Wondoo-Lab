package com.wondoo.articleservice.tempfeed.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wondoo.articleservice.feed.domain.TimeDuration;
import lombok.Builder;

import java.util.List;

@Builder
public record TempFeedSaveRequest(
        @JsonProperty("content")
        String content,
        @JsonProperty("time_logs")
        List<TimeDuration> timeLogs
) {
}
