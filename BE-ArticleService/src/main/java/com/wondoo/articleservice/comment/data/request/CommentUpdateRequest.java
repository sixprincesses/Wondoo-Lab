package com.wondoo.articleservice.comment.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentUpdateRequest {
    @JsonProperty("comment_id")
    private Long commentId;
    @JsonProperty("content")
    private String content;
    @Builder
    public CommentUpdateRequest(Long commentId, String content) {
        this.commentId = commentId;
        this.content = content;
    }
}
