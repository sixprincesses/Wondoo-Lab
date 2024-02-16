package com.wondoo.articleservice.memberinfo.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class MemberInfo {
    @Id
    @Column(name = "member_info_id")
    private Long id;
    private String nickname;
    private String imageurl;
    private String level;

    @CreatedDate
    private LocalDateTime createdTime;
    @LastModifiedDate
    private LocalDateTime updatedTime;

    @Builder
    public MemberInfo(Long id, String nickname, String imageurl, String level) {
        this.id = id;
        this.nickname = nickname;
        this.imageurl = imageurl;
        this.level = level;
    }

    public void updateMemberInfo(String nickname, String imageurl, String level) {
        this.nickname = nickname;
        this.imageurl = imageurl;
        this.level = level;
    }
}
