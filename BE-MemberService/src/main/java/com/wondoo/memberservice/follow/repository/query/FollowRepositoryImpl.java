package com.wondoo.memberservice.follow.repository.query;

import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wondoo.memberservice.follow.data.query.FollowsResponse;
import com.wondoo.memberservice.follow.data.query.QFollowsResponse;
import com.wondoo.memberservice.follow.domain.Follow;
import com.wondoo.memberservice.member.domain.QMember;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.wondoo.memberservice.follow.domain.QFollow.follow;

@RequiredArgsConstructor
public class FollowRepositoryImpl implements FollowRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<FollowsResponse> followersLoad(Long memberId, Pageable pageable) {
        List<FollowsResponse> followers = followsLoad(
                memberId, pageable, follow.from, follow.to.id);
        return new PageImpl<>(followers, pageable, followers.size());
    }


    @Override
    public Page<FollowsResponse> followingsLoad(Long memberId, Pageable pageable) {
        List<FollowsResponse> followings = followsLoad(
                memberId, pageable, follow.to, follow.from.id);
        return new PageImpl<>(followings, pageable, followings.size());
    }

    @Override
    public Optional<Follow> findByFollow(Long toId, Long fromId) {
        return Optional.ofNullable(queryFactory
                .selectFrom(follow)
                .where(follow.from.id.eq(fromId).and(follow.to.id.eq(toId)))
                .fetchOne());
    }

    @Override
    public Page<FollowsResponse> friendsLoad(Long memberId, Pageable pageable) {
        List<FollowsResponse> friends = queryFactory
                .select(new QFollowsResponse(
                        follow.to.id,
                        follow.to.nickname,
                        follow.to.imageId,
                        follow.to.level
                ))
                .from(follow)
                .where(follow.from.id.eq(memberId)
                        .and(follow.isFriend.eq(true)))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(friends, pageable, friends.size());
    }

    private List<FollowsResponse> followsLoad(Long memberId, Pageable pageable, QMember qMember, NumberPath<Long> id) {
        return queryFactory
                .select(new QFollowsResponse(
                        qMember.id,
                        qMember.nickname,
                        qMember.imageId,
                        qMember.level
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
