package com.wondoo.articleservice.github.data.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangedFileResponse {
    @JsonProperty("files")
    private List<ChangedFile> files;
}
