package com.wondoo.notificationservice.notification.data.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

public class CommentEvent extends Event{
    @JsonProperty("url")
    private Long url;
    @JsonProperty("sub_url")
    private Long subUrl;
    @JsonProperty("content")
    private String content;

    @Builder
    public CommentEvent(Long targetId, Long url, Long subUrl, String content) {
        this.targetId = targetId;
        this.url = url;
        this.content = content;
    }
}
