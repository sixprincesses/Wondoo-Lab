package com.wondoo.messageservice._global.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record Event(
        @JsonProperty("member_id")
        Long memberId,
        @JsonProperty("nickname")
        String nickname,
        @JsonProperty("image_id")
        String imageurl
) {
}
