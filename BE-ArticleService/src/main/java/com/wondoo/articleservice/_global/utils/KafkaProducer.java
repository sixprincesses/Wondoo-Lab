package com.wondoo.articleservice._global.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    @Value("${kafka.topic.notification.noti}")
    private String notificationTopic;

    @Value("${kafka.topic.article.updateinfo}")
    private String articleUpdateTopic;

    @Value("${kafka.topic.member.point}")
    private String pointUpdateTopic;

    public void sendNotificationMessage(String key, String message) {
        kafkaTemplate.send(notificationTopic, key, message);
    }

    public void sendArticleUpdateMessage(String key, String message) {
        kafkaTemplate.send(articleUpdateTopic, key, message);
    }

    public void sendPointUpdateMessage(String key, String message) {
        kafkaTemplate.send(pointUpdateTopic, key, message);
    }
}