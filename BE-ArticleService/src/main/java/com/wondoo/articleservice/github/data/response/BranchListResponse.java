package com.wondoo.articleservice.github.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BranchListResponse {
    @JsonProperty("branches")
    private List<String> branchList;

    public BranchListResponse() {
        this.branchList = new ArrayList<>();
    }
}
