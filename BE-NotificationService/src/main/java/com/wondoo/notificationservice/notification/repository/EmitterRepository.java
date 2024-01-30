package com.wondoo.notificationservice.notification.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@RequiredArgsConstructor
public class EmitterRepository {

    private final Map<Long, SseEmitter> emitterMap = new ConcurrentHashMap<>();

    public void save(Long socialId, SseEmitter emitter) {
        emitterMap.put(socialId, emitter);
    }

    public void deleteById(Long socialId) {
        emitterMap.remove(socialId);
    }

    public SseEmitter get(Long socialId) {
        return emitterMap.get(socialId);
    }
}
