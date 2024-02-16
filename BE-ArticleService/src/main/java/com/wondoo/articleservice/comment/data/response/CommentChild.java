package com.wondoo.articleservice.comment.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wondoo.articleservice.memberinfo.data.response.MemberInfoResponse;
import lombok.Builder;

import java.time.LocalDateTime;

public class CommentChild {
    @JsonProperty("comment_id")
    private Long commentId;
    @JsonProperty("content")
    private String content;
    @JsonProperty("is_deleted")
    private boolean isDeleted;

    @JsonProperty("member")
    private MemberInfoResponse member;

    @JsonProperty("feed_id")
    private Long feedId;

    @JsonProperty("created_time")
    private LocalDateTime createdTime;
    @JsonProperty("updated_time")
    private LocalDateTime updatedTime;


    @Builder
    public CommentChild(Long commentId, String content, boolean isDeleted, MemberInfoResponse member, Long feedId, LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.commentId = commentId;
        this.content = content;
        this.isDeleted = isDeleted;
        this.member = member;
        this.feedId = feedId;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }
}
