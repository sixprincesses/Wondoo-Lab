package com.wondoo.notificationservice.notification.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wondoo.notificationservice.notification.data.message.Event;
import lombok.Builder;

@Builder
public record NotificationResponse(
        @JsonProperty("id")
        String id,
        @JsonProperty("type")
        String type,
        @JsonProperty("event")
        Event event,
        @JsonProperty("read")
        Boolean read,
        @JsonProperty("time")
        Long time
) {
}
