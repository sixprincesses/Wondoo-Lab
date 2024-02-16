package com.wondoo.memberservice.member.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wondoo.memberservice.member.domain.Level;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MemberDetailResponse(
        @JsonProperty("member_id")
        Long memberId,
        @JsonProperty("image_id")
        String imageId,
        @JsonProperty("cover_image_id")
        String coverImageId,
        @JsonProperty("nickname")
        String nickname,
        @JsonProperty("name")
        String name,
        @JsonProperty("email")
        String email,
        @JsonProperty("phone")
        String phone,
        @JsonProperty("gender")
        String gender,
        @JsonProperty("is_follow")
        Boolean isFollow,
        @JsonProperty("created_time")
        LocalDateTime createTime,
        @JsonProperty("follower_count")
        Long followerCount,
        @JsonProperty("following_count")
        Long followingCount,
        @JsonProperty("level")
        Level level
) {
}
