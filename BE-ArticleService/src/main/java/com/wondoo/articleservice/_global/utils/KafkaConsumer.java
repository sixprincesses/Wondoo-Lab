package com.wondoo.articleservice._global.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wondoo.articleservice.memberinfo.data.request.MemberInfoRequest;
import com.wondoo.articleservice.memberinfo.service.MemberInfoService;
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

    @KafkaListener(topics = "${kafka.topic.member.updateinfo}", groupId = "${kafka.group.member}", containerFactory = "kafkaListener")
    public void consumeMessage(String kafkaEvent) throws JsonProcessingException {
        log.info("consumeMessage Start kafkaEvent Message : [{}]", kafkaEvent);
        MemberInfoRequest request = objectMapper.readValue(kafkaEvent, MemberInfoRequest.class);
        memberInfoService.updateMemberInfo(request);
    }
}