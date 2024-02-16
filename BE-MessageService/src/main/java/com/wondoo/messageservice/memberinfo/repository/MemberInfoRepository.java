package com.wondoo.messageservice.memberinfo.repository;

import com.wondoo.messageservice.memberinfo.data.MemberInfo;
import com.wondoo.messageservice.memberinfo.repository.query.MemberInfoRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberInfoRepository extends JpaRepository<MemberInfo, Long>, MemberInfoRepositoryCustom {
}
