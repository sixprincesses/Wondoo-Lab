package com.wondoo.memberservice.planner.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record TargetTimeRequest(
        @JsonProperty("target_time")
        Long targetTime
) {
}
