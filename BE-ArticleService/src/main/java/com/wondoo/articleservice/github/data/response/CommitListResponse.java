package com.wondoo.articleservice.github.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CommitListResponse {
    @JsonProperty("commits")
    private List<Commit> commitList;

    public CommitListResponse() {
        this.commitList = new ArrayList<>();
    }
}
