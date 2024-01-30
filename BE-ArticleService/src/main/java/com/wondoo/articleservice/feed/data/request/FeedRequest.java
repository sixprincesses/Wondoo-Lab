package com.wondoo.articleservice.feed.data.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
public class FeedRequest {
    private String feedId;

    private Long memberId;

    private String title;

    private String content;

    private List<List<LocalDateTime>> timeLogs;

}
