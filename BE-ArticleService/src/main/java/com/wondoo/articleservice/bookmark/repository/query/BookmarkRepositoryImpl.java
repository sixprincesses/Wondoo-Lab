package com.wondoo.articleservice.bookmark.repository.query;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wondoo.articleservice.bookmark.data.cond.FindBookmarkCond;
import com.wondoo.articleservice.bookmark.data.cond.FindBookmarksCond;
import com.wondoo.articleservice.bookmark.domain.Bookmark;
import com.wondoo.articleservice.bookmark.domain.QBookmark;
import com.wondoo.articleservice.feed.domain.QFeedInfo;
import com.wondoo.articleservice.memberinfo.domain.QMemberInfo;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.wondoo.articleservice.bookmark.domain.QBookmark.bookmark;
import static com.wondoo.articleservice.feed.domain.QFeedInfo.*;
import static com.wondoo.articleservice.memberinfo.domain.QMemberInfo.*;
import static org.springframework.data.domain.Sort.Direction;

@RequiredArgsConstructor
public class BookmarkRepositoryImpl implements BookmarkRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Bookmark> findBookmarkByMemberIdAndFeedId(Long memberId, Long feedId) {
        Bookmark bookmark = queryFactory.selectFrom(QBookmark.bookmark)
                .join(QBookmark.bookmark.memberInfo, memberInfo).fetchJoin()
                .join(QBookmark.bookmark.feedinfo, feedInfo).fetchJoin()
                .where(
                        QBookmark.bookmark.memberInfo.id.eq(memberId),
                        QBookmark.bookmark.feedinfo.id.eq(feedId)
                ).fetchOne();
        return Optional.ofNullable(bookmark);
    }

    @Override
    public Optional<Bookmark> findByBookmarkCond(FindBookmarkCond cond) {
        Bookmark findBookmark = queryFactory.selectFrom(bookmark)
                .join(bookmark.memberInfo, memberInfo)
                .join(bookmark.feedinfo, feedInfo)
                .where(
                        bookmark.memberInfo.id.eq(cond.memberId()),
                        bookmark.feedinfo.id.eq(cond.feedId())
                )
                .fetchOne();
        return Optional.ofNullable(findBookmark);
    }

    @Override
    public List<Bookmark> findBookmarks(FindBookmarksCond cond) {
        return queryFactory.selectFrom(bookmark)
                .join(bookmark.feedinfo, feedInfo).fetchJoin()
                .join(feedInfo.memberInfo, memberInfo).fetchJoin()
                .where(
                        ltBookmarkId(cond.lastBookmarkId()),
                        bookmark.memberInfo.id.eq(cond.memberId())
                )
                .orderBy(createOrderSpecifier(cond.direction()))
                .limit(cond.pageSize())
                .fetch();
    }

    private BooleanExpression ltBookmarkId(Long lastBookmarkId) {
        return lastBookmarkId == null ? null : bookmark.id.lt(lastBookmarkId);
    }

    private OrderSpecifier<Long> createOrderSpecifier(Direction direction) {
        if (direction == null || direction.isDescending()) {
            return bookmark.id.desc();
        }
        return bookmark.id.asc();
    }
}
