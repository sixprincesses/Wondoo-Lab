package com.wondoo.articleservice.feed.repository;

import com.wondoo.articleservice.feed.domain.Feed;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FeedRepository extends MongoRepository<Feed, String> {
    Optional<Feed> findByReference(String reference);
    void deleteByReference(String reference);
    @Query("{'memberId': ?0, 'createdTime': { $gte: ?1, $lte: ?2 } }")
    List<Feed> findFeedByMemberIdAndCreatedTimeInCurrentMonth(
            Long memberId, LocalDateTime startOfMonth, LocalDateTime endOfMonth
    );
}
