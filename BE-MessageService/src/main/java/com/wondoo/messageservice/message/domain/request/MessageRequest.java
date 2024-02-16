package com.wondoo.messageservice.message.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wondoo.messageservice._global.type.MessageType;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MessageRequest {
    @JsonProperty("sender_id")
    private Long memberId;
    @JsonProperty("content")
    private String content;
    @JsonProperty("reference")
    private String reference;
    @JsonProperty("message_type")
    private MessageType messageType;
}
