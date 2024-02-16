package com.wondoo.messageservice._global.type;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MessageType {
    @JsonProperty("CHANNEL_IN")
    CHANNEL_IN,
    @JsonProperty("CHANNEL_OUT")
    CHANNEL_OUT,
    @JsonProperty("MESSAGE_TXT")
    MESSAGE_TXT,
    @JsonProperty("MESSAGE_IMG")
    MESSAGE_IMG,
    @JsonProperty("MESSAGE_NOTIFICATION")
    MESSAGE_NOTIFICATION,
}
