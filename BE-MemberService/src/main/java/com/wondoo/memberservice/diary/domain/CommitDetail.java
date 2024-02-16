package com.wondoo.memberservice.diary.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
public class CommitDetail {
    @JsonProperty("commit_id")
    private String commitId;
    @JsonProperty("time")
    private Date time;
    @JsonProperty("content")
    private String content;

    @Builder
    public CommitDetail(String commitId, Date time, String content) {
        this.commitId = commitId;
        this.time = time;
        this.content = content;
    }
}
