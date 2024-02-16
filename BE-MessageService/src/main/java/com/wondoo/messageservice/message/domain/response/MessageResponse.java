package com.wondoo.messageservice.message.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wondoo.messageservice._global.type.MessageType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class MessageResponse {
    @JsonProperty("member_id")
    private Long memberId;
    @JsonProperty("reference")
    private String reference;
    @JsonProperty("nickname")
    private String nickname;
    @JsonProperty("image_id")
    private String imageurl;
    @JsonProperty("content")
    private String content;
    @JsonProperty("message_type")
    private MessageType messageType;
    private LocalDateTime createdTime;
}
