package com.wondoo.notificationservice.notification.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wondoo.notificationservice.notification.data.message.FollowMessage;
import com.wondoo.notificationservice.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${message.topic.name}", groupId = "member", containerFactory = "kafkaListener")
    public void consumeMessage(String message) throws IOException {
        FollowMessage followMessage = objectMapper.readValue(message, FollowMessage.class);
        notificationService.notify(followMessage.targetId(), followMessage.content());
    }
}
