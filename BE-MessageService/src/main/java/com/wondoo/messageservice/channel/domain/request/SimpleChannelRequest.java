package com.wondoo.messageservice.channel.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class SimpleChannelRequest {
    @JsonProperty("sender_id")
    Long memberId1;
    @JsonProperty("receiver_id")
    Long memberId2;
}
