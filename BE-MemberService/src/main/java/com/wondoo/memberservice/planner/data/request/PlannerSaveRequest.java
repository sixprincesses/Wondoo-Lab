package com.wondoo.memberservice.planner.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.DayOfWeek;
import java.util.List;

@Builder
public record PlannerSaveRequest(
        @JsonProperty("index")
        Integer index,
        @JsonProperty("plan_detail")
        List<PlannerDetailSaveRequest> plannerDetailSaveRequests
) {
}
