package com.wondoo.memberservice.streak.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record StreakResponse(
        @JsonProperty("grass")
        List<GrassResponse> grass,
        @JsonProperty("current_streak")
        int currentStreak
) {
    public static StreakResponse of(List<GrassResponse> grass, int currentStreak) {
        return StreakResponse.builder()
                .grass(grass)
                .currentStreak(currentStreak)
                .build();
    }
}
