package com.wondoo.messageservice.channel.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wondoo.messageservice.message.domain.response.MessageResponse;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChannelResponse {
    @JsonProperty("name")
    private String name;
    @JsonProperty("reference")
    private String reference;
    @JsonProperty("image_id")
    private String imageurl;
    @JsonProperty("last_message")
    private MessageResponse lastMessage;
}
