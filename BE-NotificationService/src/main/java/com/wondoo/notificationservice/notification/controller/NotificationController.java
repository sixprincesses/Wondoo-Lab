package com.wondoo.notificationservice.notification.controller;

import com.wondoo.notificationservice.global.annotation.RestWondooController;
import com.wondoo.notificationservice.notification.service.EmitterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestWondooController
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final EmitterService emitterService;

    @GetMapping(value = "/notification/subscribe/{member_id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(
            @PathVariable("member_id") Long member_id
    ) {

        return emitterService.subscribe(member_id);
    }
}

