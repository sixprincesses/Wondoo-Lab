package com.wondoo.notificationservice.notification.controller;

import com.wondoo.notificationservice.global.annotation.RestWondooController;
import com.wondoo.notificationservice.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestWondooController
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping(value = "/notification/subscribe/{member_id}/{last_message}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(
            @PathVariable("member_id") Long member_id,
            @PathVariable("last_message") String lastMessage
    ) throws IOException {

        return notificationService.subscribe(member_id, lastMessage);
    }
}

