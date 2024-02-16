package com.wondoo.memberservice.streak.repository;

import com.wondoo.memberservice.streak.domain.Streak;
import com.wondoo.memberservice.streak.repository.query.StreakRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreakRepository extends JpaRepository<Streak, Long>, StreakRepositoryCustom {
}
