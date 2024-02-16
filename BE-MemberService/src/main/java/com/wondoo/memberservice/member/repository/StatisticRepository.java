package com.wondoo.memberservice.member.repository;

import com.wondoo.memberservice.member.domain.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticRepository extends JpaRepository<Statistic, Long> {
}
