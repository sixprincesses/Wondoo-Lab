package com.wondoo.memberservice.global.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wondoo.memberservice.global.exception.ServerErrorCode;
import com.wondoo.memberservice.global.exception.ServerException;
import com.wondoo.memberservice.point.data.message.PointMessage;
import com.wondoo.memberservice.point.service.PointSaveService;
import jdk.jfr.Event;
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

    private final ObjectMapper objectMapper;
    private final PointSaveService pointSaveService;

    @KafkaListener(
            topics = "${kafka.topic.member.point}",
            groupId = "${kafka.group.member}",
            containerFactory = "kafkaListener"
    )
    public void consumeMessage(@Payload String kafkaEvent, @Header(KafkaHeaders.RECEIVED_KEY) String key) throws JsonProcessingException {
        log.info("Follow Kafka Message : [{}]", kafkaEvent);

        PointMessage pointMessage = objectMapper.readValue(kafkaEvent, PointMessage.class);
        pointSaveService.pointSave(pointMessage);

        throw new ServerException(ServerErrorCode.SERVER_ERROR_CODE);
    }
}
