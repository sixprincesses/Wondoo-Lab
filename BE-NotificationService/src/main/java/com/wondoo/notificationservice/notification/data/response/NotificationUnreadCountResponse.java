package com.wondoo.notificationservice.notification.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record NotificationUnreadCountResponse(
        @JsonProperty("unread_count")
        Long unreadCount
) {
}
