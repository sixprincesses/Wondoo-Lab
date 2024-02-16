package com.wondoo.articleservice.feed.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TimeDuration {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    public TimeDuration(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
