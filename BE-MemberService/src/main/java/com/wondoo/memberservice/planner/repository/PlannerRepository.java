package com.wondoo.memberservice.planner.repository;

import com.wondoo.memberservice.planner.domain.Planner;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PlannerRepository extends MongoRepository<Planner, String> {

    Optional<Planner> findByMemberIdAndDate(Long memberId, LocalDate date);
}
