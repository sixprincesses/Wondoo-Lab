package com.wondoo.articleservice.keyword.repository.query;

import com.wondoo.articleservice.keyword.data.response.KeywordRankUpdateResponse;

import java.util.List;

public interface KeywordRepositoryCustom {
    List<KeywordRankUpdateResponse> findAllKeywordRankCount();

    List<String> findAllKeywordSearchList(String keywordString);
}
