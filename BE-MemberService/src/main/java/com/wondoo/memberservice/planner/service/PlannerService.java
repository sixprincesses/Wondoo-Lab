package com.wondoo.memberservice.planner.service;

import com.wondoo.memberservice.member.domain.Member;
import com.wondoo.memberservice.member.exception.MemberErrorCode;
import com.wondoo.memberservice.member.exception.MemberException;
import com.wondoo.memberservice.member.repository.MemberRepository;
import com.wondoo.memberservice.planner.data.request.PlannerSaveAllRequest;
import com.wondoo.memberservice.planner.data.request.PlannerSaveRequest;
import com.wondoo.memberservice.planner.data.request.TargetTimeRequest;
import com.wondoo.memberservice.planner.data.response.PlannerLoadAllResponse;
import com.wondoo.memberservice.planner.domain.PlanDetail;
import com.wondoo.memberservice.planner.domain.Planner;
import com.wondoo.memberservice.planner.repository.PlannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlannerService implements PlannerSaveService, PlannerLoadService {

    private final MemberRepository memberRepository;
    private final PlannerRepository plannerRepository;

    /**
     * 목표 시간 설정 요청
     *
     * @param targetTimeRequest 목표 시간
     */
    @Transactional
    @Override
    public void targetTimeSave(Long memberId, TargetTimeRequest targetTimeRequest) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND)
                );
        member.updateTargetTime(targetTimeRequest.targetTime());
    }

    /**
     * Planner 저장 요청
     *
     * @param memberId              요청자 member_id
     * @param plannerSaveAllRequest 한 주짜리 Planner List
     */
    @Transactional
    @Override
    public void plannerSaveAll(Long memberId, PlannerSaveAllRequest plannerSaveAllRequest) {
        LocalDate today = plannerSaveAllRequest.today();
        LocalDate mondayOfThisWeek = getMondayOfThisWeek(today);

        List<PlannerSaveRequest> planner = plannerSaveAllRequest.planners();

        planner.stream()
                .map(plannerSaveRequest -> {
                    LocalDate dateWithIndex = mondayOfThisWeek.plusDays(plannerSaveRequest.index());

                    List<PlanDetail> planDetails = plannerSaveRequest.plannerDetailSaveRequests().stream()
                            .map(detailSaveRequest -> PlanDetail.builder()
                                    .title(detailSaveRequest.title())
                                    .time(detailSaveRequest.time())
                                    .build())
                            .collect(Collectors.toList());

                    Optional<Planner> plannerExist = plannerRepository.findByMemberIdAndDate(memberId, dateWithIndex);
                    if (plannerExist.isPresent()) {
                        Planner plannerDocument = plannerExist.get();
                        plannerDocument.planDetailUpdate(planDetails);
                        plannerRepository.save(plannerDocument);
                        return plannerDocument;
                    }

                    return Planner.builder()
                            .memberId(memberId)
                            .year(dateWithIndex.getYear())
                            .month(dateWithIndex.getMonthValue())
                            .date(dateWithIndex)
                            .planDetails(planDetails)
                            .build();
                })
                .forEach(plannerRepository::save);
    }


    /**
     * 목표 시간 조회
     *
     * @param memberId 요청자 member_id
     * @return 목표 시간 반환
     */
    @Override
    public TargetTimeRequest targetTimeLoad(Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND)
                );
        return TargetTimeRequest.builder()
                .targetTime(member.getTargetTime())
                .build();
    }

    /**
     * planner 조회 요청
     *
     * @param memberId 요청자 member_id
     * @param week    현재 날짜를 기준 으로 조회를 원하는 주를 구하기 위한 변수
     * @return 해당 주의 planner
     */
    @Override
    public PlannerLoadAllResponse plannerLoadAll(Long memberId, Long week) {

        LocalDate mondayOfThisWeek = getMondayOfThisWeek(LocalDate.now().plusWeeks(week));
        List<Planner> planners = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LocalDate date = mondayOfThisWeek.plusDays(i);
            Optional<Planner> planner = plannerRepository.findByMemberIdAndDate(memberId, date);
            if (planner.isPresent()){
                planners.add(planner.get());
                continue;
            }
            planners.add(null);
        }

        return PlannerLoadAllResponse.builder()
                .planners(planners)
                .build();
    }

    private LocalDate getMondayOfThisWeek(LocalDate today) {
        int dayOfWeekValue = today.getDayOfWeek().getValue();
        int diff = DayOfWeek.MONDAY.getValue() - dayOfWeekValue;
        if (diff > 0) {
            diff -= 7;
        }
        return today.plusDays(diff);
    }
}
