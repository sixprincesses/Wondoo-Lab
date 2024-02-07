package com.wondoo.notificationservice.notification.data.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FollowEvent extends Event{

    @JsonProperty("from_id")
    private Long fromId;
    @JsonProperty("from_nickname")
    private String fromNickname;

    @Builder
    public FollowEvent(Long targetId, String type, Long fromId, String fromNickname) {
        this.targetId = targetId;
        this.type = type;
        this.fromId = fromId;
        this.fromNickname = fromNickname;
    }
}
