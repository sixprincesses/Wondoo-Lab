package com.wondoo.articleservice.feed.domain;

import com.wondoo.articleservice._global.data.TimeLog;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Document
@Getter
@Builder
public class Feed {
    @Id
    private String id;

    private Long memberId;

    private String title;

    private String content;

    private List<List<LocalDateTime>> TimeLogs;

    private Long totalTime;

    @CreatedDate
    private LocalDateTime createdTime;
    @LastModifiedDate
    private LocalDateTime updatedTime;

    public void updateFeed(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
