package com.wondoo.notificationservice.notification.controller;

import com.wondoo.notificationservice.global.annotation.RestWondooController;
import com.wondoo.notificationservice.notification.data.response.NotificationResponse;
import com.wondoo.notificationservice.notification.data.response.NotificationUnreadCountResponse;
import com.wondoo.notificationservice.notification.service.EmitterService;
import com.wondoo.notificationservice.notification.service.NotificationLoadService;
import com.wondoo.notificationservice.notification.service.NotificationSaveService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestWondooController
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final EmitterService emitterService;
    private final NotificationLoadService notificationLoadService;
    private final NotificationSaveService notificationSaveService;

    @GetMapping(value = "/notification/subscribe/{member_id}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(
            @PathVariable("member_id") Long memberId
    ) {

        return emitterService.subscribe(memberId);
    }

    @GetMapping("/auth/notification")
    public ResponseEntity<Page<NotificationResponse>> notificationLoad(
            @RequestHeader("member_id") Long memberId,
            Pageable pageable
    ) {

        return ResponseEntity.ok(notificationLoadService.notificationLoad(memberId, pageable));
    }

    @PostMapping("/auth/notification/{notification_id}")
    public ResponseEntity<NotificationUnreadCountResponse> notificationRead(
            @PathVariable("notification_id") String notificationId,
            @RequestHeader("member_id") Long memberId
    ) {

        return ResponseEntity.ok(notificationSaveService.notificationRead(memberId, notificationId));
    }

    @PostMapping("/auth/notification/read")
    public ResponseEntity<NotificationUnreadCountResponse> notificationReadAll(
            @RequestHeader("member_id") Long memberId
    ) {

        return ResponseEntity.ok(notificationSaveService.notificationReadAll(memberId));
    }
}

