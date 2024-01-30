package com.wondoo.memberservice.follow.data.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class FollowersResponse {

    @JsonProperty("followers")
    private List<String> followers;
    @JsonProperty("followers_count")
    private Long followersCount;

    @QueryProjection
    @Builder
    public FollowersResponse(List<String> followers, Long followersCount) {
        this.followers = followers;
        this.followersCount = followersCount;
    }
}
