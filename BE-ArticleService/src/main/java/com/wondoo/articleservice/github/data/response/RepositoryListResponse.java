package com.wondoo.articleservice.github.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RepositoryListResponse {
    @JsonProperty("repositories")
    private List<String> repositoryList;

    public RepositoryListResponse() {
        this.repositoryList = new ArrayList<>();
    }
}
