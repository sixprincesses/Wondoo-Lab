package com.wondoo.memberservice.follow.data.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import com.wondoo.memberservice.member.domain.Level;
import lombok.Getter;

@Getter
public class FollowsResponse {

    @JsonProperty("member_id")
    private Long memberId;
    @JsonProperty("nickname")
    private String nickname;
    @JsonProperty("image_id")
    private String imageId;
    @JsonProperty("level")
    private Level level;

    @QueryProjection
    public FollowsResponse(Long memberId, String nickname, String imageId, Level level) {
        this.memberId = memberId;
        this.nickname = nickname;
        this.imageId = imageId;
        this.level = level;
    }
}
