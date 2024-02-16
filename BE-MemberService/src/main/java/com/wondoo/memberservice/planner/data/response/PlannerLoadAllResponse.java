package com.wondoo.memberservice.planner.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wondoo.memberservice.planner.domain.Planner;
import lombok.Builder;

import java.util.List;

@Builder
public record PlannerLoadAllResponse(
        @JsonProperty("planners")
        List<Planner> planners
) {
}
