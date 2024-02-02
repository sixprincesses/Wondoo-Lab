package com.wondoo.notificationservice.notification.repository;

import com.wondoo.notificationservice.notification.domain.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface NotificationRepository extends MongoRepository<Notification, String>{

    @Query(value = "{'member_id': ?0, 'read': false}", count = true)
    Long countUnreadNotificationsByMemberId(Long memberId);
}
