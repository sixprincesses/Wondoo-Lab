package com.wondoo.articleservice.memberinfo.repository;

import com.wondoo.articleservice.memberinfo.domain.MemberInfo;
import com.wondoo.articleservice.memberinfo.repository.query.MemberInfoRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberInfoRepository extends JpaRepository<MemberInfo, Long>, MemberInfoRepositoryCustom {
}
