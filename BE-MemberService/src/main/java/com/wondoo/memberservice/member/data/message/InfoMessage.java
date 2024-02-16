package com.wondoo.memberservice.member.data.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wondoo.memberservice.member.domain.Level;
import lombok.Builder;

@Builder
public record InfoMessage(
        @JsonProperty("member_id")
        Long memberId,

        @JsonProperty("level")
        String level,

        @JsonProperty("nickname")
        String nickname,
        @JsonProperty("image_id")
        String imageId
) {
}
