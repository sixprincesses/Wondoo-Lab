package com.wondoo.notificationservice.notification.data.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public abstract class Event {

    @JsonProperty("target_id")
    protected Long targetId;
}
