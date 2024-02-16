package com.wondoo.messageservice.memberinfo.data;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class MemberInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    private String imageurl;

    @CreatedDate
    private LocalDateTime createdTime;
    @LastModifiedDate
    private LocalDateTime updatedTime;

    @Builder
    public MemberInfo(Long id, String nickname, String imageurl) {
        this.id = id;
        this.nickname = nickname;
        this.imageurl = imageurl;
    }

    public void updateMemberInfo(String nickname, String imageurl) {
        this.nickname = nickname;
        this.imageurl = imageurl;
    }
}
