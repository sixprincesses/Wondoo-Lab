package com.wondoo.articleservice.keyword.repository.query;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wondoo.articleservice.keyword.data.response.KeywordRankResponse;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.wondoo.articleservice.keyword.domain.QKeyword.keyword;
import static com.wondoo.articleservice.keyword.domain.QKeywordRank.keywordRank;

@RequiredArgsConstructor
public class KeywordRankRepositoryCustomImpl implements KeywordRankRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<KeywordRankResponse> findKeywordRank() {
        List<Tuple> results = jpaQueryFactory.select(
                        keyword.name,
                        keywordRank.currCount,
                        keywordRank.prevCount)
                .from(keywordRank)
                .join(keywordRank.keyword, keyword)
                .on(keywordRank.keyword.id.eq(keyword.id))
                .orderBy(keywordRank.currCount.desc())
                .limit(10)
                .fetch();
        List<KeywordRankResponse> response = new ArrayList<>();
        Long rank = 1L;
        for (int i = 0; i < results.size(); i++) {
            Tuple result = results.get(i);
            response.add(KeywordRankResponse.builder()
                    .name(result.get(0, String.class))
                    .count(result.get(1, Long.class))
                    .gap(result.get(1, Long.class) - result.get(2, Long.class))
                    .rank(rank)
                    .build());
            if (i + 1 != results.size()
                    && results.get(i).get(1, Long.class).equals(results.get(i + 1).get(1, Long.class))) {
                continue;
            }
            rank++;
        }
        return response;
    }
}
