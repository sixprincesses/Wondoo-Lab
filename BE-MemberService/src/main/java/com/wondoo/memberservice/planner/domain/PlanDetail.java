package com.wondoo.memberservice.planner.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PlanDetail {

    @JsonProperty("title")
    private String title;

    @JsonProperty("time")
    private Long time;

    @Builder
    public PlanDetail(String title, Long time) {
        this.title = title;
        this.time = time;
    }
}
