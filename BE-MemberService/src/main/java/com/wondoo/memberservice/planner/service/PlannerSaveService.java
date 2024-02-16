package com.wondoo.memberservice.planner.service;

import com.wondoo.memberservice.planner.data.request.PlannerSaveAllRequest;
import com.wondoo.memberservice.planner.data.request.TargetTimeRequest;

public interface PlannerSaveService {

    void targetTimeSave(Long memberId, TargetTimeRequest targetTimeRequest);

    void plannerSaveAll(Long memberId, PlannerSaveAllRequest plannerSaveAllRequest);
}
