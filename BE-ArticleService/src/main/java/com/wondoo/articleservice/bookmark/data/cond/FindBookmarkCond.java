package com.wondoo.articleservice.bookmark.data.cond;

import lombok.Builder;

@Builder
public record FindBookmarkCond(
        Long memberId,
        Long feedId
) {
    public static FindBookmarkCond of(Long memberId, Long feedId) {
        return FindBookmarkCond.builder()
                .memberId(memberId)
                .feedId(feedId)
                .build();
    }
}
