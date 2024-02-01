package com.wondoo.notificationservice.notification.data.cache;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record NotificationCache(
        @JsonProperty("content")
        String content,
        @JsonProperty("time")
        Long time
) {
}
