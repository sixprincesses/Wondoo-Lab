package com.wondoo.articleservice.feed.repository;

import com.wondoo.articleservice.feed.domain.Feed;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedRepository extends MongoRepository<Feed, String> {
    List<Feed> findByIdIn(List<String> feedIds);
}
