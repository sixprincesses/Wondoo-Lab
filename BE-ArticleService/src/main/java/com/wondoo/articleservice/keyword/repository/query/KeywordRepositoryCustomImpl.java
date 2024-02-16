package com.wondoo.articleservice.keyword.repository.query;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wondoo.articleservice.keyword.data.response.KeywordRankUpdateResponse;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.wondoo.articleservice.keyword.domain.QKeyword.keyword;
import static com.wondoo.articleservice.keyword.domain.QKeywordInfo.keywordInfo;

@RequiredArgsConstructor
public class KeywordRepositoryCustomImpl implements KeywordRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<KeywordRankUpdateResponse> findAllKeywordRankCount() {
        List<Tuple> results = jpaQueryFactory.select(keyword.name, keyword.name.count())
                .from(keywordInfo)
                .join(keywordInfo.keyword, keyword)
                .on(keywordInfo.keyword.id.eq(keyword.id))
                .groupBy(keyword.name)
                .fetch();
        List<KeywordRankUpdateResponse> response = new ArrayList<>();
        for (Tuple result : results) {
            response.add(KeywordRankUpdateResponse.builder()
                    .name(result.get(0, String.class))
                    .count(result.get(1, Long.class))
                    .build());
        }
        return response;
    }

    @Override
    public List<String> findAllKeywordSearchList(String keywordString) {
        return jpaQueryFactory.select(keyword.name)
                .from(keyword)
                .where(keyword.name.like("%" +keywordString + "%"))
                .limit(10)
                .fetch();
    }
}