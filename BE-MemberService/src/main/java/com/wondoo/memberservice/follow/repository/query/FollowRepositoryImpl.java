package com.wondoo.memberservice.follow.repository.query;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wondoo.memberservice.follow.data.query.FollowersResponse;
import com.wondoo.memberservice.follow.data.query.QFollowersResponse;
import com.wondoo.memberservice.follow.domain.QFollow;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.wondoo.memberservice.follow.domain.QFollow.*;

@RequiredArgsConstructor
public class FollowRepositoryImpl implements FollowRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<FollowersResponse> followersLoad(Long memberId, Pageable pageable) {

        List<FollowersResponse> followers = queryFactory
                .select(new QFollowersResponse(
                        follow.from.nickname
                )).from(follow)
                .where(follow.to.id.eq(memberId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        JPAQuery<Long> countQuery = queryFactory
                .select(follow.count())
                .from(follow)
                .where(follow.from.id.eq(memberId));

        return PageableExecutionUtils.getPage(followers, pageable, countQuery::fetchOne);
    }
}
