package com.wondoo.articleservice.feed.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.wondoo.articleservice.feed.domain.QFeedMember.*;

@RequiredArgsConstructor
public class FeedMemberRepositoryImpl implements FeedMemberRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<String> findByRecentFeedId(Long id) {
        return jpaQueryFactory.select(feedMember.feedId)
                .from(feedMember)
                .where(feedMember.id.loe(id))
                .limit(20)
                .fetch();
    }
}
