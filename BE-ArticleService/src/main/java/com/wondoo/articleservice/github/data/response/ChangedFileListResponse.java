package com.wondoo.articleservice.github.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ChangedFileListResponse {
    @JsonProperty("changedfiles")
    private List<ChangedFile> changedFileList;

    public ChangedFileListResponse() {
        this.changedFileList = new ArrayList<>();
    }
}
