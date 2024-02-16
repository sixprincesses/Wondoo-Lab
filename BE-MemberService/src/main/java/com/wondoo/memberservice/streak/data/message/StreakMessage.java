package com.wondoo.memberservice.streak.data.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record StreakMessage(
        @JsonProperty("member_id")
        Long memberId,
        @JsonProperty("date")
        LocalDate date
) {
}
