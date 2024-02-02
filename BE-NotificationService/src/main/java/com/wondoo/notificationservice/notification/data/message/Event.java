package com.wondoo.notificationservice.notification.data.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record Event(
        @JsonProperty("target_id")
        Long targetId,
        @JsonProperty("type")
        String type,
        @JsonProperty("content")
        String content
) {
}
