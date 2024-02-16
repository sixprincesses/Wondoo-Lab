package com.wondoo.memberservice.planner.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record PlannerSaveAllRequest(
        @JsonProperty("planners")
        List<PlannerSaveRequest> planners,
        @JsonProperty("today")
        LocalDate today
) {
}
