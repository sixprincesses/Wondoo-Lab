package com.wondoo.articleservice.like.data.cond;

import lombok.Builder;

@Builder
public record FindLikeCond(
        Long memberId,
        Long feedId
) {
    public static FindLikeCond of(Long memberId, Long feedId) {
        return FindLikeCond.builder()
                .memberId(memberId)
                .feedId(feedId)
                .build();
    }
}
