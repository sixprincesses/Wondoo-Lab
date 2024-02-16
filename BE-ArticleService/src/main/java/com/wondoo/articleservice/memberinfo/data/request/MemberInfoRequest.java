package com.wondoo.articleservice.memberinfo.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MemberInfoRequest(
        @JsonProperty("member_id")
        Long memberId,
        @JsonProperty("nickname")
        String nickname,
        @JsonProperty("image_id")
        String imageurl,
        @JsonProperty("level")
        String level
) {
}
