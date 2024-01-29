package com.wondoo.articleservice.feed.repository;

import com.wondoo.articleservice.feed.domain.Feed;
import com.wondoo.articleservice.feed.repository.query.FeedRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedRepository extends MongoRepository<Feed, String>, FeedRepositoryCustom {
    
}
