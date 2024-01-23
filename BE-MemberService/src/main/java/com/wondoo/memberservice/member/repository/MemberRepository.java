package com.wondoo.memberservice.member.repository;

import com.wondoo.memberservice.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findBySocialId(Long socialId);
}
