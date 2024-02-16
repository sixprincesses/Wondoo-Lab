package com.wondoo.messageservice.channel.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ChannelListResponse {
    @JsonProperty("channels")
    List<ChannelResponse> channelList;

    public ChannelListResponse() {
        this.channelList = new ArrayList<>();
    }
}
