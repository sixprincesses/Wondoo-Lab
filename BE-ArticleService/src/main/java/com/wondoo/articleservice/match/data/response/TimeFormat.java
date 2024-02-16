package com.wondoo.articleservice.match.data.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TimeFormat {
    private Long day;
    private Long hour;
    private Long minute;
    private Long second;

    @Builder
    public TimeFormat(Long day, Long hour, Long minute, Long second) {
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }
}
