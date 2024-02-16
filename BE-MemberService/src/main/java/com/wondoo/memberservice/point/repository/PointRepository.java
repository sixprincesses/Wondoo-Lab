package com.wondoo.memberservice.point.repository;

import com.wondoo.memberservice.point.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {
}
