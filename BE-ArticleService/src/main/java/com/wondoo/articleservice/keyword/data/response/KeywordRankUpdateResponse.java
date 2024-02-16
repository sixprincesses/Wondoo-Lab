package com.wondoo.articleservice.keyword.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class KeywordRankUpdateResponse {
    @JsonProperty("name")
    private String name;
    @JsonProperty("count")
    private Long count;

    @Builder
    public KeywordRankUpdateResponse(String name, Long count) {
        this.name = name;
        this.count = count;
    }
}
