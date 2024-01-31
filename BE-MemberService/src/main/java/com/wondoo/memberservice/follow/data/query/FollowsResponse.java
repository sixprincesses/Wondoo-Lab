package com.wondoo.memberservice.follow.data.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class FollowsResponse {

    @JsonProperty("nickname")
    private String nickname;

    @QueryProjection
    public FollowsResponse(String nickname) {
        this.nickname = nickname;
    }
}
