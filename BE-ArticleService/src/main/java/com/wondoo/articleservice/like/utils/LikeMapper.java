package com.wondoo.articleservice.like.utils;

import com.wondoo.articleservice.like.domain.Like;
import org.springframework.stereotype.Component;

@Component
public class LikeMapper {

    public Like toEntity(Long memberId, Long feedId) {
        return Like.builder()
                .memberId(memberId)
                .feedId(feedId)
                .build();
    }
}
