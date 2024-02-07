package com.wondoo.notificationservice.notification.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record NotificationUnreadCountResponse(
        @JsonProperty("id")
        String id,
        @JsonProperty("target_id")
        Long targetId,
        @JsonProperty("type")
        String type,
        @JsonProperty("read")
        Boolean read,
        @JsonProperty("content")
        String content,
        @JsonProperty("time")
        Long time,
        @JsonProperty("unread_count")
        Long unreadCount
) {
}
