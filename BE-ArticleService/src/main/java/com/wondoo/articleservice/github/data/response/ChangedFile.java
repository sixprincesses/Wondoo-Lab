package com.wondoo.articleservice.github.data.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangedFile {
    @JsonProperty("filename")
    private String filename;
    @JsonProperty("patch")
    private String patch;
}
