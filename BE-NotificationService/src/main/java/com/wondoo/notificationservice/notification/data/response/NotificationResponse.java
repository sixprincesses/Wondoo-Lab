package com.wondoo.notificationservice.notification.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record NotificationResponse(
        @JsonProperty("type")
        String type,
        @JsonProperty("read")
        Boolean read,
        @JsonProperty("content")
        String content,
        @JsonProperty("time")
        Long time
) {
}
