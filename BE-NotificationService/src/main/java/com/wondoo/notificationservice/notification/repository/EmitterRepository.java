package com.wondoo.notificationservice.notification.repository;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class EmitterRepository {

    private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter save(Long emitterId, SseEmitter sseEmitter) {
        emitters.put(emitterId,sseEmitter);
        return sseEmitter;
    }

    public Map<Long, SseEmitter> findAllEmitters() {
        return new HashMap<>(emitters);
    }

    public Optional<SseEmitter> get(Long memberId) {
        return Optional.ofNullable(emitters.get(memberId));
    }


    public void deleteById(Long emitterId) {
        emitters.remove(emitterId);
    }
}
