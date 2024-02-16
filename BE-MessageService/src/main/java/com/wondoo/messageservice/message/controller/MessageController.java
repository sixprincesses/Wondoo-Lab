package com.wondoo.messageservice.message.controller;

import com.wondoo.messageservice._global.annotation.RestWondooController;
import com.wondoo.messageservice._global.data.ApiResponse;
import com.wondoo.messageservice._global.data.StatusCode;
import com.wondoo.messageservice.message.domain.request.MessageRequest;
import com.wondoo.messageservice.message.domain.response.MessageListResponse;
import com.wondoo.messageservice.message.domain.response.MessageResponse;
import com.wondoo.messageservice.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestWondooController
@RequiredArgsConstructor
@Slf4j
public class MessageController {
    private final MessageService messageService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    private static final String BROKER_PREFIX = "/server";

    @MessageMapping("/message/{reference}")
    public void sendMessage(@Payload MessageRequest request, @DestinationVariable String reference){
        log.info("[MESSAGE] {} → {} [{}]", request.getMemberId(), reference, request.getContent());
        MessageListResponse message = messageService.saveMessage(request);
        ApiResponse<MessageListResponse> response = new ApiResponse<>(StatusCode.SUCCESS_INSERT, message);
        simpMessagingTemplate.convertAndSend(BROKER_PREFIX + "/channel/" + reference,
                ResponseEntity.status(HttpStatus.OK).body(response));
    }
}
