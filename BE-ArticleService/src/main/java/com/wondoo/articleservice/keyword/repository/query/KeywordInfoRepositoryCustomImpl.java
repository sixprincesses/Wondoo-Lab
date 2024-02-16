package com.wondoo.articleservice.keyword.repository.query;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wondoo.articleservice.keyword.data.response.KeywordCountResponse;
import com.wondoo.articleservice.keyword.domain.KeywordInfo;
import com.wondoo.articleservice.keyword.domain.QKeywordInfo;
import kotlin.Pair;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

import static com.wondoo.articleservice.feed.domain.QFeedInfo.feedInfo;
import static com.wondoo.articleservice.keyword.domain.QKeyword.keyword;
import static com.wondoo.articleservice.keyword.domain.QKeywordInfo.keywordInfo;

@RequiredArgsConstructor
public class KeywordInfoRepositoryCustomImpl implements KeywordInfoRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    private static BooleanExpression ltFeedId(Long feedId) {
        return feedId == null ? null : keywordInfo.feedId.lt(feedId);
    }

    @Override
    public List<KeywordCountResponse> findKeywordCountByMemberId(Long memberId) {
        List<Tuple> results = jpaQueryFactory.select(keyword.name, keyword.count())
                .from(keywordInfo)
                .join(keywordInfo.keyword, keyword)
                .on(keywordInfo.keyword.id.eq(keyword.id))
                .where(keywordInfo.memberId.eq(memberId))
                .groupBy(keyword.name)
                .orderBy(keyword.name.count().desc())
                .fetch();
        List<KeywordCountResponse> response = new ArrayList<>();
        for (Tuple result : results) {
            response.add(KeywordCountResponse.builder()
                    .name(result.get(0, String.class))
                    .count(result.get(1, Long.class))
                    .build());
        }
        return response;
    }

    @Override
    public List<KeywordCountResponse> findKeywordCountWithZeroByMemberId(Long memberId) {
        List<Tuple> results = jpaQueryFactory
                .select(keywordInfo.keyword.name, keywordInfo.keyword.id.count())
                .from(keywordInfo)
                .where(keywordInfo.memberId.eq(memberId))
                .groupBy(keywordInfo.keyword.id)
                .fetch();
        HashMap<String, Long> keywordCountList = new HashMap<>();
        for (Tuple result : results) {
            keywordCountList.put(
                    result.get(0, String.class),
                    result.get(1, Long.class)
            );
        }

        List<Tuple> tempResults = jpaQueryFactory
                .select(keywordInfo.keyword.name, Expressions.constant(0L))
                .from(keywordInfo)
                .where(keywordInfo.memberId.ne(memberId))
                .groupBy(keywordInfo.keyword.id)
                .fetch();
        for (Tuple result : tempResults) {
            if(keywordCountList.containsKey(result.get(0, String.class))){
                continue;
            }
            keywordCountList.put(result.get(0, String.class), 0L);
        }
        List<KeywordCountResponse> response = new ArrayList<>();
        List<String> sortedKeys = new ArrayList<>(keywordCountList.keySet());
        Collections.sort(sortedKeys);
        for (String key : sortedKeys) {
            response.add(KeywordCountResponse.builder()
                    .name(key)
                    .count(keywordCountList.get(key))
                    .build());
        }
        return response;
    }

    @Override
    public List<KeywordInfo> findKeywordFeedList(Long feedId, Long keywordId, int size) {
        return jpaQueryFactory.selectFrom(keywordInfo)
                .where(
                        keywordInfo.keyword.id.eq(keywordId),
                        ltFeedId(feedId)
                )
                .orderBy(keywordInfo.id.desc())
                .limit(size)
                .fetch();
    }
}
