package com.wondoo.articleservice.like.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wondoo.articleservice.like.domain.Like;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record LikeResponse(
        @JsonProperty("like_id")
        Long likeId,
        @JsonProperty("feed_id")
        Long feedId,
        @JsonProperty("created_time")
        LocalDateTime createdTime
) {
    public static LikeResponse from(Like like) {
        return LikeResponse.builder()
                .likeId(like.getId())
                .feedId(like.getFeedId())
                .createdTime(like.getUpdatedTime())
                .build();
    }
}
