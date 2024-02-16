package com.wondoo.articleservice.keyword.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

public class KeywordSearchResponse {
    @JsonProperty("search_keywords")
    List<String> keywordList;
    @Builder
    public KeywordSearchResponse(List<String> keywordList) {
        this.keywordList = keywordList;
    }
}
