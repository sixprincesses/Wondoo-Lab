package com.wondoo.articleservice.keyword.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class KeywordCountListResponse {
    @JsonProperty("keywords")
    private List<KeywordCountResponse> keywordCountList;
}
