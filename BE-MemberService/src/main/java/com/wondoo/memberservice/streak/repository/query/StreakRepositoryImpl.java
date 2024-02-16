package com.wondoo.memberservice.streak.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wondoo.memberservice.streak.domain.Streak;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.wondoo.memberservice.streak.domain.QStreak.streak;

@RequiredArgsConstructor
public class StreakRepositoryImpl implements StreakRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<Streak> findStreak(Long memberId, LocalDate date) {
        Streak findStreak = queryFactory.selectFrom(streak)
                .where(
                        streak.member.id.eq(memberId),
                        streak.date.eq(date)
                ).fetchOne();
        return Optional.ofNullable(findStreak);
    }

    @Override
    public List<Streak> findStreaks(Long memberId, LocalDate now) {
        return queryFactory.selectFrom(streak)
                .where(
                        streak.date.after(now.minusDays(365)),
                        streak.member.id.eq(memberId)
                )
                .orderBy(streak.date.desc())
                .fetch();
    }
}
