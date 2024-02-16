package com.wondoo.articleservice.comment.domain;

import jakarta.persistence.*;
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
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    private String content;
    private boolean isDeleted;

    private Long feedId;
    private Long memberId;
    private Long parentId;
    @CreatedDate
    private LocalDateTime createdTime;
    @LastModifiedDate
    private LocalDateTime updatedTime;

    @Builder
    public Comment(String content, boolean isDeleted, Long feedId, Long memberId, Long parentId) {
        this.content = content;
        this.isDeleted = isDeleted;
        this.feedId = feedId;
        this.memberId = memberId;
        this.parentId = parentId;
    }

    public void updateComment(String content) {
        this.content = content;
    }

    public void deleteComment() {
        this.isDeleted = true;
    }
}
