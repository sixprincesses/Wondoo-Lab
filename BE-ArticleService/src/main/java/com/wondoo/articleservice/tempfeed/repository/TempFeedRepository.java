package com.wondoo.articleservice.tempfeed.repository;

import com.wondoo.articleservice.tempfeed.domain.TempFeed;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TempFeedRepository extends MongoRepository<TempFeed, Long> {
}
