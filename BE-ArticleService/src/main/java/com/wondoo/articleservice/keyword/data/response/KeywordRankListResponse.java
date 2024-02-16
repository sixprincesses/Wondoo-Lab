package com.wondoo.articleservice.keyword.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public class KeywordRankListResponse {
    @JsonProperty("ranks")
    List<KeywordRankResponse> keywordRankList;
}
