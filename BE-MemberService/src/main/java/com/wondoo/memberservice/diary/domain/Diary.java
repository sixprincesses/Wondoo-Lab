package com.wondoo.memberservice.diary.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Document(collection = "daily_diary")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary {

    @Id
    private String id;

    @Field("member_id")
    private Long memberId;

    @Field("content")
    private String content;

    @Field("date")
    private LocalDate date;

    @Field("repository")
    private Set<String> repository;

    @Field("commit_count")
    private Integer commitCount;

    @Field("log")
    private Map<String, List<CommitDetail>> log;

    @Builder
    public Diary(Long memberId, String content, LocalDate date, Set<String> repository, Integer commitCount, Map<String, List<CommitDetail>> log) {
        this.memberId = memberId;
        this.content = content;
        this.date = date;
        this.repository = repository;
        this.commitCount = commitCount;
        this.log = log;
    }

    public void DiaryUpdate(String content, Set<String> repository, Integer commitCount, Map<String, List<CommitDetail>> log) {
        this.content = content;
        this.repository = repository;
        this.commitCount = commitCount;
        this.log = log;
    }
}
