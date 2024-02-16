package com.wondoo.articleservice.memberinfo.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wondoo.articleservice.feed.domain.Feed;
import com.wondoo.articleservice.memberinfo.domain.MemberInfo;
import lombok.Builder;

@Builder
public record MemberInfoResponse(
        @JsonProperty("member_id")
        Long memberId,
        @JsonProperty("nickname")
        String nickname,
        @JsonProperty("image_id")
        String imageurl,
        @JsonProperty("level")
        String level
) {
    public static MemberInfoResponse of(Feed feed, MemberInfo memberInfo) {
        return MemberInfoResponse.builder()
                .memberId(feed.getMemberId())
                .nickname(memberInfo.getNickname())
                .imageurl(memberInfo.getImageurl())
                .level(memberInfo.getLevel())
                .build();
    }
}
