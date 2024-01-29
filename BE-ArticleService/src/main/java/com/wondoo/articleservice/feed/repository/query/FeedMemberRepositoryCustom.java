package com.wondoo.articleservice.feed.repository.query;

import java.util.List;

public interface FeedMemberRepositoryCustom {
    List<String> findByRecentFeedId(Long id);
}
