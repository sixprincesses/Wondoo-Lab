package com.wondoo.articleservice._global.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record PointMessage(
        @JsonProperty("member_id")
        Long memberId,
        @JsonProperty("type")
        String type,
        @JsonProperty("point")
        Integer point,
        @JsonProperty("time")
        Long time
) {
}
