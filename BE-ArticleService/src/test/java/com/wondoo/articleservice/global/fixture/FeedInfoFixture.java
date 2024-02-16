package com.wondoo.articleservice.global.fixture;

import com.wondoo.articleservice.feed.domain.FeedInfo;

import static java.util.UUID.randomUUID;

public enum FeedInfoFixture {
    FEED_INFO(1L, randomUUID().toString());

    private final Long feedInfoId;
    private final String UUID;

    FeedInfoFixture(Long feedInfoId, String UUID) {
        this.feedInfoId = feedInfoId;
        this.UUID = UUID;
    }

    public static FeedInfo makeSavedFeedInfo(FeedInfoFixture fixture) {
        return FeedInfo.builder()
                .reference(fixture.UUID)
                .build();
    }

    public static FeedInfo makeFeedInfo(FeedInfoFixture fixture) {
        return FeedInfo.builder()
                .reference(fixture.UUID)
                .build();
    }
}
