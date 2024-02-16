package com.wondoo.memberservice.streak.repository.query;

import com.wondoo.memberservice.streak.domain.Streak;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StreakRepositoryCustom {
    Optional<Streak> findStreak(Long memberId, LocalDate date);

    List<Streak> findStreaks(Long memberId, LocalDate now);
}
