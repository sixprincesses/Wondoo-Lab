package com.wondoo.messageservice._global.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wondoo.messageservice._global.data.MemberInfoEventRequest;
import com.wondoo.messageservice.memberinfo.service.MemberInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {
    private final ObjectMapper objectMapper;
    private final MemberInfoService memberInfoService;

    @KafkaListener(
            topics = "${kafka.topic.member.updateinfo}",
            groupId = "memberinfo",
            containerFactory = "kafkaListener"
    )
    public void consumeMessage(String kafkaEvent) throws JsonProcessingException {
        MemberInfoEventRequest request = objectMapper.readValue(kafkaEvent, MemberInfoEventRequest.class);
        memberInfoService.updateMemberInfo(request);
    }
}
