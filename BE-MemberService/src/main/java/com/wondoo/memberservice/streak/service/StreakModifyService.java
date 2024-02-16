package com.wondoo.memberservice.streak.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wondoo.memberservice.streak.domain.Streak;

public interface StreakModifyService {
    Streak update(String key, String message) throws JsonProcessingException;
}
