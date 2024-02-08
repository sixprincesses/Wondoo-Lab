package com.wondoo.notificationservice.notification.domain;

import com.wondoo.notificationservice.notification.data.message.Event;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Document(collection = "notification")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

    @Id
    private String id;

    @Field("member_id")
    private Long memberId;

    @Field("type")
    private String type;

    @Field("event")
    private Event event;

    @Field("read")
    private Boolean read;

    @Field("time")
    private Long time;

    @Builder
    public Notification(Long memberId, String type, Event event, Boolean read, Long time) {
        this.memberId = memberId;
        this.type = type;
        this.event = event;
        this.read = read;
        this.time = time;
    }

    public void notificationRead() {
        this.read = true;
    }
}
