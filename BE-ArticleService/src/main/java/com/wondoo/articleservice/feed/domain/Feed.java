package com.wondoo.articleservice.feed.domain;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Document
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Feed {
    @Id
    private String id;

    private Long memberId;

    private String title;

    private String content;

    private List<List<LocalDateTime>> timeLogs;

    private Long totalTime;

    @CreatedDate
    private LocalDateTime createdTime;
    @LastModifiedDate
    private LocalDateTime updatedTime;

    @Builder
    public Feed(String id, Long memberId, String title, String content, List<List<LocalDateTime>> timeLogs, Long totalTime) {
        this.id = id;
        this.memberId = memberId;
        this.title = title;
        this.content = content;
        this.timeLogs = timeLogs;
        this.totalTime = totalTime;
    }

    public void updateFeed(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
