package com.wondoo.notificationservice.notification.service;

import com.wondoo.notificationservice.notification.data.response.NotificationUnreadCountResponse;

public interface NotificationSaveService {

    NotificationUnreadCountResponse notificationRead(Long memberId, String notificationId);

    NotificationUnreadCountResponse notificationReadAll(Long memberId);
}
