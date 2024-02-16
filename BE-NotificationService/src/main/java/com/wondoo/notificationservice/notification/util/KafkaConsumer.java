package com.wondoo.notificationservice.notification.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wondoo.notificationservice.notification.data.message.CommentEvent;
import com.wondoo.notificationservice.notification.data.message.Event;
import com.wondoo.notificationservice.notification.data.message.FollowEvent;
import com.wondoo.notificationservice.notification.data.message.LikeEvent;
import com.wondoo.notificationservice.notification.exception.NotificationErrorCode;
import com.wondoo.notificationservice.notification.exception.NotificationException;
import com.wondoo.notificationservice.notification.service.EmitterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final EmitterService emitterService;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "${kafka.topic.notification.noti}",
            groupId = "${kafka.group.noti}",
            containerFactory = "kafkaListener"
    )
    public void consumeMessage(@Payload String kafkaEvent, @Header(KafkaHeaders.RECEIVED_KEY) String key) throws JsonProcessingException {
        log.info("Follow Kafka Message : [{}]", kafkaEvent);
        if (key.equals("follow")) {
            Event event = objectMapper.readValue(kafkaEvent, FollowEvent.class);
            emitterService.kafkaListen(key, event);
            return;
        }
        if (key.equals("comment")) {
            Event event = objectMapper.readValue(kafkaEvent, CommentEvent.class);
            emitterService.kafkaListen(key, event);
            return;
        }
        if (key.equals("comment_comment")) {
            Event event = objectMapper.readValue(kafkaEvent, CommentEvent.class);
            emitterService.kafkaListen(key, event);
            return;
        }
        if (key.equals("like")) {
            Event event = objectMapper.readValue(kafkaEvent, LikeEvent.class);
            emitterService.kafkaListen(key, event);
            return;
        }

        throw new NotificationException(NotificationErrorCode.NOTIFICATION_WRONG_ACCESS);
    }
}
