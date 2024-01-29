package com.wondoo.articleservice.feed.repository.query;

import com.wondoo.articleservice.feed.domain.Feed;

import java.time.LocalDateTime;
import java.util.List;

public interface FeedRepositoryCustom {
    List<Feed> findRandomFeeds(Long feedId);
}
