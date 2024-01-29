package com.wondoo.memberservice.global.utils;

import com.wondoo.memberservice.follow.message.FollowMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaProvider {

    @Value(value = "${message.topic.name}")
    private String topicName;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void FollowMessage(FollowMessage message) {
        kafkaTemplate.send(topicName, message);
    }
}