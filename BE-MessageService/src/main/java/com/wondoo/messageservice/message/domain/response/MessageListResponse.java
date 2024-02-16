package com.wondoo.messageservice.message.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MessageListResponse {
    @JsonProperty("messages")
    List<MessageResponse> messageList;

    public MessageListResponse() {
        this.messageList = new ArrayList<>();
    }
}
