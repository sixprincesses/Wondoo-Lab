package com.wondoo.memberservice.streak.service;

import com.wondoo.memberservice.streak.data.response.StreakResponse;

public interface StreakLoadService {
    StreakResponse findMemberStreak(Long memberId);
}
