package com.wondoo.notificationservice.notification.data.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LikeEvent extends Event{
    @JsonProperty("url")
    private Long url;
    @JsonProperty("content")
    private String content;

    @Builder
    public LikeEvent(Long targetId, Long url, String content) {
        this.targetId = targetId;
        this.url = url;
        this.content = content;
    }
}
