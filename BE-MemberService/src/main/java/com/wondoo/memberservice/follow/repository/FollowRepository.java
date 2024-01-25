package com.wondoo.memberservice.follow.repository;

import com.wondoo.memberservice.follow.domain.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}
