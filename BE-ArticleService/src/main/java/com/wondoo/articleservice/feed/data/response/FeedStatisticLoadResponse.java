package com.wondoo.articleservice.feed.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record FeedStatisticLoadResponse(
        @JsonProperty("feed_statistic")
        List<FeedStatisticDetailResponse> feedStatistic
) {
}
