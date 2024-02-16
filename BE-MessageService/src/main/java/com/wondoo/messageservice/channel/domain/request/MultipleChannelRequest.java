package com.wondoo.messageservice.channel.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wondoo.messageservice._global.type.AccessType;
import lombok.Getter;

@Getter
public class MultipleChannelRequest {
    @JsonProperty("member_id")
    private Long memberId;
    private String name;
    @JsonProperty("access_type")
    private AccessType accessType;
}
