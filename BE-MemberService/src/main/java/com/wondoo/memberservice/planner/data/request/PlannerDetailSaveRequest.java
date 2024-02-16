package com.wondoo.memberservice.planner.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record PlannerDetailSaveRequest(
        @JsonProperty("title")
        String title,
        @JsonProperty("time")
        Long time
) {
}
