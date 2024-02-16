package com.wondoo.memberservice.planner.service;

import com.wondoo.memberservice.planner.data.request.TargetTimeRequest;
import com.wondoo.memberservice.planner.data.response.PlannerLoadAllResponse;

import java.time.LocalDate;

public interface PlannerLoadService {
    TargetTimeRequest targetTimeLoad(Long memberId);

    PlannerLoadAllResponse plannerLoadAll(Long memberId, Long week);
}
