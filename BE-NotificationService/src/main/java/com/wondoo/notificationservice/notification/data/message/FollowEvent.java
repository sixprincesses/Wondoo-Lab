package com.wondoo.notificationservice.notification.data.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FollowEvent extends Event{

    @JsonProperty("url")
    private Long url;
    @JsonProperty("content")
    private String content;

    @Builder
    public FollowEvent(Long targetId, Long url, String content) {
        this.targetId = targetId;
        this.url = url;
        this.content = content;
    }
}
