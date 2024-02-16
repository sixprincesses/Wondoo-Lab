package com.wondoo.articleservice.feed.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
public class FeedListResponse {
    @JsonProperty("last_feed_id")
    private Long lastFeedId;
    @JsonProperty("feeds")
    private List<FeedResponse> feedsResponse;
    public FeedListResponse() {
        this.feedsResponse = new ArrayList<>();
    }
    public void updateLastFeedId(Long lastFeedId){
        this.lastFeedId = lastFeedId;
    }
}
