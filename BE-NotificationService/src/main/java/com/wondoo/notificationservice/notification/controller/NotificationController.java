package com.wondoo.notificationservice.notification.controller;

import com.wondoo.notificationservice.global.annotation.RestWondooController;
import com.wondoo.notificationservice.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestWondooController
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping(value = "/notification/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(
            @RequestHeader("social_id") Long social_id
    ) throws IOException {

        return notificationService.subscribe(social_id);
    }
}
