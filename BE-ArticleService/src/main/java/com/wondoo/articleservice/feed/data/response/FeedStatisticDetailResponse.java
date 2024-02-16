package com.wondoo.articleservice.feed.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;


@Builder
public record FeedStatisticDetailResponse(
        @JsonProperty("year_month")
        String yearMonth,
        @JsonProperty("total_time")
        Long totalTime
) {
}
