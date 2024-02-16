package com.wondoo.articleservice.comment.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wondoo.articleservice.memberinfo.data.response.MemberInfoResponse;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CommentParent {
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
    @JsonProperty("parent_id")
    private Long parentId;
    @JsonProperty("replies")
    private List<CommentChild> replylist;

    @JsonProperty("created_time")
    private LocalDateTime createdTime;
    @JsonProperty("updated_time")
    private LocalDateTime updatedTime;
    public void setReplylist(List<CommentChild> replylist){
        this.replylist = replylist;
    }
    @Builder
    public CommentParent(Long commentId, String content, boolean isDeleted, MemberInfoResponse member, Long feedId, Long parentId, List<CommentChild> replylist, LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.commentId = commentId;
        this.content = content;
        this.isDeleted = isDeleted;

        this.member = member;

        this.feedId = feedId;
        this.parentId = parentId;
        this.replylist = replylist;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }
}