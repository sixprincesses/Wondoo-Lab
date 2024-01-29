package com.wondoo.articleservice.feed.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wondoo.articleservice.feed.domain.Feed;
import com.wondoo.articleservice.feed.domain.QFeedMember;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FeedRepositoryImpl implements FeedRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Feed> findRandomFeeds(Long feedId) {
        QFeedMember feedMember = QFeedMember.feedMember;
        // feedMember feedId 기준 20개 선택
        //return jpaQueryFactory.selectFrom();
        return null;
    }
}
