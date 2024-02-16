package com.wondoo.articleservice.feed.repository.query;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wondoo.articleservice.feed.domain.FeedInfo;
import com.wondoo.articleservice.feed.domain.QFeedInfo;
import com.wondoo.articleservice.memberinfo.domain.QMemberInfo;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.wondoo.articleservice.feed.domain.QFeedInfo.*;
import static com.wondoo.articleservice.feed.domain.QFeedInfo.feedInfo;
import static com.wondoo.articleservice.memberinfo.domain.QMemberInfo.*;

@RequiredArgsConstructor
public class FeedInfoRepositoryCustomImpl implements FeedInfoRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    private BooleanExpression ltFeedId(Long feedId) {
        return feedId == null ? null : feedInfo.id.lt(feedId);
    }

    @Override
    public List<FeedInfo> findRecentFeedList(Long feedId, int size) {
        return jpaQueryFactory.selectFrom(feedInfo)
                .where(ltFeedId(feedId))
                .orderBy(feedInfo.id.desc())
                .limit(size)
                .fetch();
    }

    @Override
    public List<FeedInfo> findMemberFeedList(Long feedId, Long memberId, int size) {
        return jpaQueryFactory.selectFrom(feedInfo)
                .join(feedInfo.memberInfo, memberInfo).fetchJoin()
                .where(
                        feedInfo.memberInfo.id.eq(memberId),
                        ltFeedId(feedId)
                )
                .orderBy(feedInfo.id.desc())
                .limit(size)
                .fetch();
    }

    @Override
    public Optional<FeedInfo> findLastFeedByMemberId(Long memberId) {
        FeedInfo feedInfo = jpaQueryFactory.selectFrom(QFeedInfo.feedInfo)
                .join(QFeedInfo.feedInfo.memberInfo, memberInfo).fetchJoin()
                .where(QFeedInfo.feedInfo.memberInfo.id.eq(memberId))
                .orderBy(QFeedInfo.feedInfo.id.desc())
                .limit(1)
                .fetchOne();
        return Optional.ofNullable(feedInfo);
    }
}
