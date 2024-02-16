package com.wondoo.messageservice.channel.controller;

import com.wondoo.messageservice._global.annotation.RestWondooController;
import com.wondoo.messageservice._global.data.ApiResponse;
import com.wondoo.messageservice._global.data.StatusCode;
import com.wondoo.messageservice.channel.domain.request.MultipleChannelRequest;
import com.wondoo.messageservice.channel.domain.request.SimpleChannelRequest;
import com.wondoo.messageservice.channel.domain.response.ChannelListResponse;
import com.wondoo.messageservice.channel.service.ChannelService;
import com.wondoo.messageservice.message.domain.response.MessageListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.web.bind.annotation.*;

@RestWondooController
@RequiredArgsConstructor
@Slf4j
public class ChannelController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    private final ChannelService channelService;

    private static final String BROKER_PREFIX = "/server";

    @SubscribeMapping("/channel/connection/{memberId}")
    public void updateChannelList(@DestinationVariable Long memberId) {
        ChannelListResponse channelList = channelService.findChannelListByMemberId(memberId);
        ApiResponse<ChannelListResponse> response = new ApiResponse<>(StatusCode.SUCCESS_SELECT, channelList);
        simpMessagingTemplate.convertAndSend(BROKER_PREFIX + "/channel/connection/" + memberId,
                ResponseEntity.status(HttpStatus.OK).body(response));
    }

    @GetMapping("/channel/{reference}")
    public ResponseEntity<ApiResponse<MessageListResponse>> updateMessageList(@PathVariable String reference) {
        MessageListResponse messageList = channelService.findMessageListByChannelId(reference);
        ApiResponse<MessageListResponse> response = new ApiResponse<>(StatusCode.SUCCESS_SELECT, messageList);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @MessageMapping("/channel/create/simple")
    public void createSimpleChannel(@Payload SimpleChannelRequest request) {
        channelService.createSimpleChannel(request);
        updateChannelList(request.getMemberId1());
        updateChannelList(request.getMemberId2());
    }

    @MessageMapping("/channel/create/multiple")
    public void createMultipleChannel(@Payload MultipleChannelRequest request) {
        channelService.createMultipleChannel(request);
        updateChannelList(request.getMemberId());
    }
}
