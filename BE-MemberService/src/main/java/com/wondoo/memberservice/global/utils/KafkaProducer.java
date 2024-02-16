package com.wondoo.memberservice.global.utils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value(value = "${kafka.topic.notification.noti}")
    private String notificationTopic;
    @Value(value = "${kafka.topic.member.updateinfo}")
    private String updateInfoTopic;
    @Value(value = "${kafka.topic.member.diary}")
    private String diaryTopic;

    public void sendNotificationMessage(String key, String message) {
        kafkaTemplate.send(notificationTopic, key, message);
    }

    public void sendInfoMessage(String message) {
        kafkaTemplate.send(updateInfoTopic, message);
    }

    public void sendDiaryMessage(String key, String message) {
        kafkaTemplate.send(diaryTopic, key, message);
    }
}