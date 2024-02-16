package com.wondoo.articleservice.like.repository.query;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wondoo.articleservice.like.data.cond.FindLikeCond;
import com.wondoo.articleservice.like.data.cond.FindLikesCond;
import com.wondoo.articleservice.like.domain.Like;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.wondoo.articleservice.like.domain.QLike.like;
import static org.springframework.data.domain.Sort.Direction;

@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Like> findByLikeCond(FindLikeCond cond) {
        Like findLike = queryFactory.selectFrom(like)
                .where(
                        like.memberId.eq(cond.memberId()),
                        like.feedId.eq(cond.feedId())
                ).fetchOne();
        return Optional.ofNullable(findLike);
    }

    @Override
    public List<Like> findLikesCond(FindLikesCond cond) {
        return queryFactory.selectFrom(like)
                .where(
                        loeLikeId(cond.lastLikeId()),
                        like.memberId.eq(cond.memberId())
                )
                .orderBy(createOrderSpecifier(cond.direction()))
                .limit(cond.pageSize())
                .fetch();
    }

    private BooleanExpression loeLikeId(Long lastLikeId) {
        return lastLikeId == null ? null : like.id.loe(lastLikeId);
    }

    private OrderSpecifier<Long> createOrderSpecifier(Direction direction) {
        if (direction == null || direction.isDescending()) {
            return like.id.desc();
        }
        return like.id.asc();
    }
}
