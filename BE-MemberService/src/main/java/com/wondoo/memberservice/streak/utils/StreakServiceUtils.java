package com.wondoo.memberservice.streak.utils;

import com.wondoo.memberservice.member.domain.Member;
import com.wondoo.memberservice.streak.data.message.StreakMessage;
import com.wondoo.memberservice.streak.domain.Streak;
import com.wondoo.memberservice.streak.repository.StreakRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.NoSuchElementException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StreakServiceUtils {
    public static Streak findByKafkaMessageOrDefault(StreakRepository streakRepository,
                                                     StreakMessage streakMessage,
                                                     Member member) {
        return streakRepository.findStreak(
                        streakMessage.memberId(),
                        streakMessage.date())
                .orElseGet(() -> Streak.builder()
                        .member(member)
                        .build()
                );
    }

    public static Streak findByKafkaMessage(StreakRepository streakRepository, StreakMessage streakMessage) {
        return streakRepository.findStreak(
                        streakMessage.memberId(),
                        streakMessage.date())
                .orElseThrow(() -> new NoSuchElementException("해당 스트릭이 존재하지 않습니다"));
    }
}
