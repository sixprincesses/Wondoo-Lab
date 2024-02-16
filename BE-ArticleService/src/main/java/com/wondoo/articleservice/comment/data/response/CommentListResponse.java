package com.wondoo.articleservice.comment.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
@Getter
public class CommentListResponse {
    @JsonProperty("comments")
    List<CommentParent> commentList;
    public CommentListResponse() {
        this.commentList = new ArrayList<>();
    }
}
