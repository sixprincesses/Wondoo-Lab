package com.wondoo.articleservice.like.repository;

import com.wondoo.articleservice.like.domain.Like;
import com.wondoo.articleservice.like.repository.query.LikeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long>, LikeRepositoryCustom {
    Optional<Like> findLikeByMemberIdAndFeedId(Long memberId, Long feedId);
}
