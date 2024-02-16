package com.wondoo.messageservice.channel.data;

import com.wondoo.messageservice._global.type.AccessType;
import com.wondoo.messageservice._global.type.ChannelType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Channel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(unique = true)
    private String reference;

    private AccessType accessType;
    private ChannelType channelType;

    @CreatedDate
    private LocalDateTime createdTime;
    @LastModifiedDate
    private LocalDateTime updatedTime;

    @Builder
    public Channel(String name, String reference, AccessType accessType, ChannelType channelType) {
        this.name = name;
        this.reference = reference;
        this.accessType = accessType;
        this.channelType = channelType;
    }
}
