package com.wondoo.articleservice.feed.repository;

import com.wondoo.articleservice.feed.domain.FeedInfo;
import com.wondoo.articleservice.feed.repository.query.FeedInfoRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedInfoRepository extends JpaRepository<FeedInfo, Long>, FeedInfoRepositoryCustom {
    Optional<FeedInfo> findByReference(String reference);

    List<FeedInfo> findAllByMemberInfo_Id(Long memberId);
}