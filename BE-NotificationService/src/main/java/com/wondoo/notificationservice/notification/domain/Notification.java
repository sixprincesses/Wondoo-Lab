package com.wondoo.notificationservice.notification.domain;

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

    @Field("read")
    private Boolean read;

    @Field("content")
    private String content;

    @Field("time")
    private Long time;

    @Builder
    public Notification(Long memberId, String type, Boolean read, String content, Long time) {
        this.memberId = memberId;
        this.type = type;
        this.read = read;
        this.content = content;
        this.time = time;
    }

    public void notificationRead() {
        this.read = true;
    }
}
