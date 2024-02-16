package com.wondoo.memberservice.planner.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;

@Getter
@Document(collection = "daily_plan")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Planner {

    @Id
    private String id;

    @Field("member_id")
    private Long memberId;

    @Field("year")
    private Integer year;

    @Field("month")
    private Integer month;

    @Field("date")
    private LocalDate date;

    @Field("plan_detail")
    private List<PlanDetail> planDetails;

    @Builder
    public Planner(Long memberId, Integer year, Integer month, LocalDate date, List<PlanDetail> planDetails) {
        this.memberId = memberId;
        this.year = year;
        this.month = month;
        this.date = date;
        this.planDetails = planDetails;
    }

    public void planDetailUpdate(List<PlanDetail> planDetails) {
        this.planDetails = planDetails;
    }
}
