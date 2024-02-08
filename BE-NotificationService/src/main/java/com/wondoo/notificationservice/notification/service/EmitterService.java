package com.wondoo.notificationservice.notification.service;

import com.wondoo.notificationservice.notification.data.message.Event;
import com.wondoo.notificationservice.notification.data.response.HeartBeat;
import com.wondoo.notificationservice.notification.data.response.NotificationUnreadCountResponse;
import com.wondoo.notificationservice.notification.domain.Notification;
import com.wondoo.notificationservice.notification.repository.EmitterRepository;
import com.wondoo.notificationservice.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmitterService {

    private final EmitterRepository emitterRepository;
    private final NotificationRepository notificationRepository;

    /**
     * Kafka 토픽마다 이벤트 발생 시 SSE 전송 요청
     * 메시지 DB에 백업
     *
     * @param event SSE 수신할 Emitter key
     */
    public void kafkaListen(String key, Event event) {

        Optional<SseEmitter> sseEmitter = emitterRepository.get(event.getTargetId());

        Notification notification = notificationRepository.save(
                Notification.builder()
                        .memberId(event.getTargetId())
                        .type(key)
                        .event(event)
                        .read(false)
                        .time(System.currentTimeMillis())
                        .build()
        );

        Long unreadCount = notificationRepository.countUnreadNotificationsByMemberId(event.getTargetId());

        sseEmitter.ifPresent(
                emitter -> sendToClient(
                        emitter,
                        event.getTargetId(),
                        NotificationUnreadCountResponse.builder()
                                .id(notification.getId())
                                .type(notification.getType())
                                .event(notification.getEvent())
                                .read(notification.getRead())
                                .time(notification.getTime())
                                .unreadCount(unreadCount)
                                .build()
                )
        );
    }

    /**
     * SSE 구독 처리
     * 동일 요청 사용자가 받은 마지막 알림 이후 들어왔던 알림이 있다면 구독과 동시에 밀린 알림들 전송
     *
     * @param memberId 사용자 member_id 당 Emitter 할당
     * @return SSE Emitter
     */
    public SseEmitter subscribe(Long memberId) {

        SseEmitter emitter = emitterRepository.save(memberId, new SseEmitter(3600L * 100));
        emitter.onCompletion(() -> {
            emitterRepository.deleteById(memberId);
        });
        emitter.onTimeout(() -> {
            emitterRepository.deleteById(memberId);
        });

        Long unreadCount = notificationRepository.countUnreadNotificationsByMemberId(memberId);

        sendToClient(
                emitter,
                memberId,
                NotificationUnreadCountResponse.builder()
                        .unreadCount(unreadCount)
                        .build()
        );

        return emitter;
    }

    /**
     * 1분마다 연결 상태 확인
     * TimeOut 과는 다른 상태 확인 메서드
     */
    @Scheduled(fixedRate = 6000)
    public void sendHeartbeat() {
        Map<Long, SseEmitter> emitters = emitterRepository.findAllEmitters();
        emitters.forEach((key, emitter) -> {
            try {
                emitter.send(emitter.event()
                        .id(String.valueOf(key))
                        .data(
                                HeartBeat.builder()
                                        .time(System.currentTimeMillis())
                                        .build()
                        ));
            } catch (IOException e) {
                emitterRepository.deleteById(key);
            }
        });
    }

    private void sendToClient(SseEmitter emitter, Long memberId, Object message) {
        try {
            emitter.send(SseEmitter.event()
                    .id(String.valueOf(memberId))
                    .data(message));
        } catch (IOException e) {
            emitterRepository.deleteById(memberId);
        }
    }
}
