package com.wondoo.notificationservice.notification.service;

import com.wondoo.notificationservice.notification.data.response.NotificationResponse;
import com.wondoo.notificationservice.notification.data.response.NotificationUnreadCountResponse;
import com.wondoo.notificationservice.notification.domain.Notification;
import com.wondoo.notificationservice.notification.exception.NotificationErrorCode;
import com.wondoo.notificationservice.notification.exception.NotificationException;
import com.wondoo.notificationservice.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService implements NotificationSaveService, NotificationLoadService {

    private final NotificationRepository notificationRepository;

    /**
     * 페이지네이션 정보 기반 알림 목록 조회
     *
     * @param memberId 조회할 기준 member_id
     * @param pageable 페이지네이션 정보 (page_size, page_offset)
     * @return Page 정보
     */
    @Override
    public Page<NotificationResponse> notificationLoad(Long memberId, Pageable pageable) {

        return notificationRepository.findByMemberId(memberId, pageable);
    }

    /**
     * 단일 알림 읽음 처리
     * @param memberId member_id
     * @param notificationId notification_id
     * @return member_id에 해당하는 알림이면 읽음 처리 후 안읽은 알림 개수 반환
     */
    @Override
    public NotificationUnreadCountResponse notificationRead(Long memberId, String notificationId) {

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(
                        () -> new NotificationException(NotificationErrorCode.NOTIFICATION_NOT_FOUND)
                );
        if (notification.getEvent().getTargetId() != memberId) {
            throw new NotificationException(NotificationErrorCode.NOTIFICATION_WRONG_ACCESS);
        }
        notification.notificationRead();
        notificationRepository.save(notification);
        Long unreadCount = notificationRepository.countUnreadNotificationsByMemberId(memberId);

        return NotificationUnreadCountResponse.builder()
                .unreadCount(unreadCount)
                .build();
    }

    /**
     * 요청자 알림 모두 읽음 처리
     * @param memberId member_id
     * @return 모두 읽음 처리 후 안읽은 알림 개수 반환
     */
    @Override
    public NotificationUnreadCountResponse notificationReadAll(Long memberId) {

        List<Notification> notifications = notificationRepository.findByMemberId(memberId);
        for (Notification notification : notifications) {
            notification.notificationRead();
            notificationRepository.save(notification);
        }
        return NotificationUnreadCountResponse.builder()
                .unreadCount(0L)
                .build();
    }
}
