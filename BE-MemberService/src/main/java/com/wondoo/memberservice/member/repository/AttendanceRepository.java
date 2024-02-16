package com.wondoo.memberservice.member.repository;

import com.wondoo.memberservice.member.domain.Attendance;
import com.wondoo.memberservice.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findByDateAndMember(LocalDate date, Member member);
}
