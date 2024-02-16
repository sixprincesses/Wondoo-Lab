package com.wondoo.articleservice.tempfeed.domain;

import com.wondoo.articleservice.feed.domain.TimeDuration;
import jakarta.persistence.EntityListeners;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "temp_feed")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class TempFeed {

    @Id
    private Long memberId;
    @Field("content")
    private String content;
    @Field("time_logs")
    private List<TimeDuration> timeLogs;
    @Field("total_time")
    private Long totalTime;

    @Builder
    public TempFeed(Long memberId, String content, List<TimeDuration> timeLogs, Long totalTime) {
        this.memberId = memberId;
        this.content = content;
        this.timeLogs = timeLogs;
        this.totalTime = totalTime;
    }

    public void updateTempFeed(String content, List<TimeDuration> timeLogs, Long totalTime) {
        this.content = content;
        this.timeLogs = timeLogs;
        this.totalTime = totalTime;
    }
}
