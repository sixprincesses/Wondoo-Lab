package com.wondoo.notificationservice.notification.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wondoo.notificationservice.notification.data.message.Event;
import com.wondoo.notificationservice.notification.data.message.FollowEvent;
import com.wondoo.notificationservice.notification.service.EmitterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final EmitterService emitterService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${message.topic.name}", groupId = "member", containerFactory = "kafkaListener")
    public void consumeMessage(String kafkaEvent) throws JsonProcessingException {

        Event event = objectMapper.readValue(kafkaEvent, FollowEvent.class);
        emitterService.kafkaListen(event);
    }
}
