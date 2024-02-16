package com.wondoo.articleservice.github.data.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommitResponse {
    @JsonProperty("sha")
    private String sha;

    @JsonProperty("commit")
    private Commit commit;

    @Getter
    @RequiredArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private class Commit {
        @JsonProperty("message")
        private String message;
    }

    public String getMessage() {
        return commit.getMessage();
    }
}
