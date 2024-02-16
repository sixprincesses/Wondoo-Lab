package com.wondoo.notificationservice.notification.repository;

import com.wondoo.notificationservice.notification.data.response.NotificationResponse;
import com.wondoo.notificationservice.notification.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String>{

    @Query(value = "{'member_id': ?0, 'read': false}", count = true)
    Long countUnreadNotificationsByMemberId(Long memberId);

    Page<NotificationResponse> findByMemberId(Long memberId, Pageable pageable);

    List<Notification> findByMemberId(Long memberId);
}
