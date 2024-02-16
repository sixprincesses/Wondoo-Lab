package com.wondoo.articleservice.feed.repository.query;

import com.wondoo.articleservice.feed.domain.FeedInfo;

import java.util.List;
import java.util.Optional;

public interface FeedInfoRepositoryCustom {
    List<FeedInfo> findRecentFeedList(Long feedId, int size);

    List<FeedInfo> findMemberFeedList(Long feedId, Long memberId, int size);

    Optional<FeedInfo> findLastFeedByMemberId(Long memberId);
}
