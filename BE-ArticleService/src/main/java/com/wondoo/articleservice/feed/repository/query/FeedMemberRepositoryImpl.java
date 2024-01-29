package com.wondoo.articleservice.feed.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wondoo.articleservice.feed.domain.QFeedMember;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FeedMemberRepositoryImpl implements FeedMemberRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<String> findByRecentFeedId(Long id) {
        QFeedMember feedMember = QFeedMember.feedMember;
        return jpaQueryFactory.select(feedMember.feedId)
                .from(feedMember)
                .where(feedMember.id.loe(id))
                .limit(20)
                .fetch();
    }
}
