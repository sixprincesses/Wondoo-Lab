package com.wondoo.notificationservice.notification.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record HeartBeat(
        @JsonProperty("heartbeat")
        Long time
) {
}
