package com.wondoo.articleservice.feed.repository;

import com.wondoo.articleservice.feed.domain.FeedMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedMemberRepository extends JpaRepository<FeedMember, Long> {

}
