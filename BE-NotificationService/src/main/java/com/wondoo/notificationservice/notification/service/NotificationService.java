package com.wondoo.notificationservice.notification.service;

import com.wondoo.notificationservice.notification.repository.EmitterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final EmitterRepository emitterRepository;

    /**
     * SSE 구독 처리
     *
     * @param socialId 사용자 social_id 당 Emitter 할당
     * @return SSE Emitter
     */
    public SseEmitter subscribe(Long socialId) {
        SseEmitter emitter = createEmitter(socialId);

        sendToClient(socialId, "EventStream Created. [member=" + socialId + "]");
        return emitter;
    }

    /**
     * 이벤트에 대한 메시지를 받을 수신자에 맞게 전송
     *
     * @param socialId SSE 이벤트 수신자
     * @param event    SSE 전송 내용
     */
    public void notify(Long socialId, Object event) {
        sendToClient(socialId, event);
    }

    private void sendToClient(Long socialId, Object data) {
        SseEmitter emitter = emitterRepository.get(socialId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().id(String.valueOf(socialId)).name("sse").data(data));
            } catch (IOException exception) {
                emitterRepository.deleteById(socialId);
                emitter.completeWithError(exception);
            }
        }
    }

    private SseEmitter createEmitter(Long socialId) {

        SseEmitter emitter = new SseEmitter();
        emitterRepository.save(socialId, emitter);

        // Emitter가 완료될 때(모든 데이터가 성공적으로 전송된 상태) Emitter를 삭제한다.
        emitter.onCompletion(() -> emitterRepository.deleteById(socialId));
        // Emitter가 타임아웃 되었을 때(지정된 시간동안 어떠한 이벤트도 전송되지 않았을 때) Emitter를 삭제한다.
        emitter.onTimeout(() -> emitterRepository.deleteById(socialId));

        return emitter;
    }

}
