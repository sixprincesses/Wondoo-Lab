package com.wondoo.articleservice.keyword.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

public class KeywordRankResponse {
    @JsonProperty("name")
    private String name;
    @JsonProperty("rank")
    private Long rank;
    @JsonProperty("count")
    private Long count;
    @JsonProperty("gap")
    private Long gap;

    @Builder
    public KeywordRankResponse(String name, Long rank, Long count, Long gap) {
        this.name = name;
        this.rank = rank;
        this.count = count;
        this.gap = gap;
    }
}
