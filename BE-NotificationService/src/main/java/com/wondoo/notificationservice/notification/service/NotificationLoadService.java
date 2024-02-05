package com.wondoo.notificationservice.notification.service;

import com.wondoo.notificationservice.notification.data.response.NotificationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationLoadService {

    Page<NotificationResponse> notificationLoad(Long memberId, Pageable pageable);
}
