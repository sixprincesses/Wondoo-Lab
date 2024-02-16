package com.wondoo.memberservice.member.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wondoo.memberservice.member.domain.Level;
import lombok.Builder;

@Builder
public record MemberRankingInfoResponse(
        @JsonProperty("member_id")
        Long memberId,
        @JsonProperty("nickname")
        String nickname,
        @JsonProperty("image_id")
        String imageId,
        @JsonProperty("level")
        Level level,
        @JsonProperty("score")
        Double score
) {
}
