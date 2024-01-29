package com.wondoo.articleservice.feed.data.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ResponseFeed {
    private String feedId;

    private Long memberId;

    private String nickname;

    private String title;

    private String content;

    private List<List<LocalDateTime>> timeLogs;

    private Long totalTime;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;
}
