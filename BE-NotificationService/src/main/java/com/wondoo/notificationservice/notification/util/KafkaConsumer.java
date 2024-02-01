package com.wondoo.notificationservice.notification.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wondoo.notificationservice.notification.data.message.FollowMessage;
import com.wondoo.notificationservice.notification.repository.EmitterRepository;
import com.wondoo.notificationservice.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${message.topic.name}", groupId = "member", containerFactory = "kafkaListener")
    public void consumeMessage(String message) throws IOException {

        FollowMessage followMessage = objectMapper.readValue(message, FollowMessage.class);
        notificationService.kafkaListen(followMessage.targetId(), message);
    }
}
