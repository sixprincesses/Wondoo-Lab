package com.wondoo.articleservice.like.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record LikesResponse(
        @JsonProperty("likes")
        List<LikeResponse> likes,
        @JsonProperty("last_like_id")
        Long lastLikeId
) {
    public static LikesResponse of(List<LikeResponse> likes, Long lastLikeId) {
        return LikesResponse.builder()
                .likes(likes)
                .lastLikeId(lastLikeId)
                .build();
    }
}
