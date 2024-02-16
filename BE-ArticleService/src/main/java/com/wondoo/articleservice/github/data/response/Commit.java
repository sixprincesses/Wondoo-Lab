package com.wondoo.articleservice.github.data.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class Commit {
    @JsonProperty("sha")
    private String sha;
    @JsonProperty("message")
    private String message;

    @Builder
    public Commit(String sha, String message) {
        this.sha = sha;
        this.message = message;
    }
}
