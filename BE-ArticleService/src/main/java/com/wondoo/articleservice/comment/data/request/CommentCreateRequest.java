package com.wondoo.articleservice.comment.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CommentCreateRequest {
    @JsonProperty("feed_id")
    private Long feedId;
    @JsonProperty("parent_id")
    private Long parentId;
    @JsonProperty("content")
    private String content;
}
