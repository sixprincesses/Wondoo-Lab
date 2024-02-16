package com.wondoo.articleservice.keyword.repository.query;

import com.wondoo.articleservice.keyword.data.response.KeywordRankResponse;

import java.util.List;

public interface KeywordRankRepositoryCustom {
    List<KeywordRankResponse> findKeywordRank();
}
