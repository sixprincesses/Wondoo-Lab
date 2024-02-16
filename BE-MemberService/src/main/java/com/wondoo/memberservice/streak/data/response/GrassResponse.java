package com.wondoo.memberservice.streak.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wondoo.memberservice.streak.domain.Streak;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
public record GrassResponse(
        @JsonProperty("date")
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate date,
        @JsonProperty("count")
        int count
) {
    public static GrassResponse from(Streak streak) {
        return GrassResponse.builder()
                .date(streak.getDate())
                .count(streak.getCount())
                .build();
    }
}
