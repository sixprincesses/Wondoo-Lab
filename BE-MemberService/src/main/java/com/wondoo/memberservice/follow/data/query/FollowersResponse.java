package com.wondoo.memberservice.follow.data.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FollowersResponse {

    @JsonProperty("follower_nickname")
    private String followerNickname;

    @QueryProjection
    public FollowersResponse(String followerNickname) {
        this.followerNickname = followerNickname;
    }
}
