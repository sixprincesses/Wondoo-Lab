package com.wondoo.articleservice.match.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MatchFeedResponse {
    @JsonProperty("similarity")
    private Double similarity;

    @Builder
    public MatchFeedResponse(Double similarity) {
        this.similarity = similarity;
    }
}
