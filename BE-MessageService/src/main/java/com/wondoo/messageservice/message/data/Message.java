package com.wondoo.messageservice.message.data;

import com.wondoo.messageservice._global.type.MessageType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private MessageType messageType;

    @CreatedDate
    private LocalDateTime createdTime;

    private Long memberId;
    private String reference;

    @Builder
    public Message(String content, MessageType messageType, Long memberId, String reference) {
        this.content = content;
        this.messageType = messageType;
        this.memberId = memberId;
        this.reference = reference;
    }
}
