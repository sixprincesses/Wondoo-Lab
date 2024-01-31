package com.wondoo.memberservice.follow.repository.query;

import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wondoo.memberservice.follow.data.query.FollowsResponse;
import com.wondoo.memberservice.follow.data.query.QFollowsResponse;
import com.wondoo.memberservice.follow.domain.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Optional;

import static com.wondoo.memberservice.follow.domain.QFollow.*;

@RequiredArgsConstructor
public class FollowRepositoryImpl implements FollowRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<FollowsResponse> followersLoad(Long memberId, Pageable pageable) {

        List<FollowsResponse> followers = followsLoad(
                memberId, pageable, follow.from.nickname, follow.to.id);
        JPAQuery<Long> countQuery = followCount(memberId, follow.to.id);

        return PageableExecutionUtils.getPage(followers, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<FollowsResponse> followingsLoad(Long memberId, Pageable pageable) {

        List<FollowsResponse> followers = followsLoad(
                memberId, pageable, follow.to.nickname, follow.from.id);
        JPAQuery<Long> countQuery = followCount(memberId, follow.from.id);

        return PageableExecutionUtils.getPage(followers, pageable, countQuery::fetchOne);
    }

    @Override
    public Optional<Follow> findByFollow(Long toId, Long fromId) {

        return Optional.ofNullable(queryFactory
                .selectFrom(follow)
                .where(follow.from.id.eq(fromId).and(follow.to.id.eq(toId)))
                .fetchOne());
    }

    private List<FollowsResponse> followsLoad(Long memberId, Pageable pageable, StringPath nickname, NumberPath<Long> id) {
        return queryFactory
                .select(new QFollowsResponse(
                        nickname
                )).from(follow)
                .where(id.eq(memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private JPAQuery<Long> followCount(Long memberId, NumberPath<Long> id) {
        return queryFactory
                .select(follow.count())
                .from(follow)
                .where(id.eq(memberId));
    }
}
