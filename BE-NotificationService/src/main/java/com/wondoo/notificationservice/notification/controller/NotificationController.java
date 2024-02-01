package com.wondoo.notificationservice.notification.controller;

import com.wondoo.notificationservice.global.annotation.RestWondooController;
import com.wondoo.notificationservice.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestWondooController
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping(value = "/notification/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(
            @RequestHeader("social_id") Long socialId,
            @RequestHeader("last_message") String lastMessage
    ) throws IOException {
        log.info("hi");
        return notificationService.subscribe(socialId, lastMessage);
    }
}

