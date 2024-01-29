package com.wondoo.articleservice.feed.repository;

import com.wondoo.articleservice.feed.domain.FeedMember;
import com.wondoo.articleservice.feed.repository.query.FeedMemberRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedMemberRepository extends JpaRepository<FeedMember, Long>, FeedMemberRepositoryCustom {
    FeedMember findByFeedId(String feedId);

}
