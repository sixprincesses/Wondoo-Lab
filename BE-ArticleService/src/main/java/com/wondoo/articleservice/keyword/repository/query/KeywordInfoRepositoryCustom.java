package com.wondoo.articleservice.keyword.repository.query;

import com.wondoo.articleservice.keyword.data.response.KeywordCountResponse;
import com.wondoo.articleservice.keyword.domain.KeywordInfo;

import java.util.List;

public interface KeywordInfoRepositoryCustom {
    List<KeywordCountResponse> findKeywordCountByMemberId(Long memberId);
    List<KeywordCountResponse> findKeywordCountWithZeroByMemberId(Long memberId);
    List<KeywordInfo> findKeywordFeedList(Long feedId, Long keywordId, int size);
}
