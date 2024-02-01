package com.wondoo.notificationservice.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wondoo.notificationservice.notification.data.cache.NotificationCache;
import com.wondoo.notificationservice.notification.data.message.FollowMessage;
import com.wondoo.notificationservice.notification.repository.EmitterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final EmitterRepository emitterRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    /**
     * Kafka 토픽마다 이벤트 발생 시 SSE 전송 요청
     *
     * @param targetId     SSE 수신할 Emitter key
     * @param kafkaMessage SSE 수신할 내용
     */
    public void kafkaListen(Long targetId, String kafkaMessage) throws JsonProcessingException {

        FollowMessage followMessage = objectMapper.readValue(kafkaMessage, FollowMessage.class);
        long time = System.currentTimeMillis();
        NotificationCache notificationCache = NotificationCache.builder()
                .content(followMessage.content())
                .time(time)
                .build();
        String message = objectMapper.writeValueAsString(notificationCache);
        // Redis 캐싱용 백업 (하루)
        redisTemplate.opsForZSet()
                .add(String.valueOf(targetId),
                        message,
                        time);
        redisTemplate.expire(String.valueOf(targetId), 24, TimeUnit.HOURS);

        Optional<SseEmitter> sseEmitter = emitterRepository.get(targetId);
        if (sseEmitter.isEmpty()) {
            return;
        }

        sendToClient(sseEmitter.get(), targetId, notificationCache);
    }

    /**
     * SSE 구독 처리
     * 동일 요청 사용자가 받은 마지막 알림 이후 들어왔던 알림이 있다면 구독과 동시에 밀린 알림들 전송
     *
     * @param socialId 사용자 social_id 당 Emitter 할당
     * @return SSE Emitter
     */
    public SseEmitter subscribe(Long socialId, String lastMessage) throws JsonProcessingException {

        SseEmitter emitter = emitterRepository.save(socialId, new SseEmitter(3600L * 100));
        emitter.onCompletion(() -> {
            emitterRepository.deleteById(socialId);
        });
        emitter.onTimeout(() -> {
            emitterRepository.deleteById(socialId);
        });
        sendToClient(emitter, socialId, "EventStream Created. [member=" + socialId + "]");
        if (!lastMessage.isEmpty()) {
            Set<String> notificationsByTimeRange = getNotificationsByTimeRange(socialId, Long.valueOf(lastMessage));
            for (String notification : notificationsByTimeRange) {
                NotificationCache notificationCache = objectMapper.readValue(notification, NotificationCache.class);
                sendToClient(emitter, socialId, notificationCache);
            }
        }

        return emitter;
    }

    /**
     * 1분마다 연결 상태 확인
     * TimeOut 과는 다른 상태 확인 메서드
     */
    @Scheduled(fixedRate = 60000)
    public void sendHeartbeat() {
        Map<Long, SseEmitter> emitters = emitterRepository.findAllEmitters();
        emitters.forEach((key, emitter) -> {
            try {
                emitter.send(SseEmitter.event().id(String.valueOf(key)).name("heartbeat").data(""));
            } catch (IOException e) {
                emitterRepository.deleteById(key);
            }
        });
    }

    private void sendToClient(SseEmitter emitter, Long socialId, Object message) {
        try {
            emitter.send(SseEmitter.event()
                    .id(String.valueOf(socialId))
                    .data(message));
        } catch (IOException e) {
            emitterRepository.deleteById(socialId);
        }
    }

    private Set<String> getNotificationsByTimeRange(Long socialId, Long lastMessageTime) {
        long min = lastMessageTime;
        long max = Long.MAX_VALUE;

        return redisTemplate.opsForZSet()
                .rangeByScore(String.valueOf(socialId), min, max);
    }
}
