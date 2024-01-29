package com.wondoo.articleservice.feed.data.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class RequestFeed {
    private String feedId;

    private Long memberId;

    private String title;

    private String content;

    private List<List<LocalDateTime>> timeLogs;

}
