package com.wondoo.storageservice.storage.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record FilesResponse(
        @JsonProperty("filesources")
        List<String> filesources
) {
}