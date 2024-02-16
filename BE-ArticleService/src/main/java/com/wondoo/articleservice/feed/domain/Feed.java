package com.wondoo.articleservice.feed.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Feed {
    @Id
    @Column(name = "reference")
    private String reference;
    @Column(name = "member_id")
    private Long memberId;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    private List<TimeDuration> timeDurationList;
    private Long totalTime;
    private List<String> keyword;
    @CreatedDate
    private LocalDateTime createdTime;
    @LastModifiedDate
    private LocalDateTime updatedTime;

    @Builder
    public Feed(String reference, Long memberId, String title, String content, List<TimeDuration> timeDurationList, Long totalTime, List<String> keyword) {
        this.reference = reference;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.timeDurationList = timeDurationList;
        this.totalTime = totalTime;
        this.keyword = keyword;
    }

    public void updateFeed(String title, String content, List<String> keyword) {
        this.title = title;
        this.content = content;
        this.keyword = keyword;
    }
}
