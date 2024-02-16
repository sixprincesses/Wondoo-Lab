package com.wondoo.storageservice.storage.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record FilesRequest(
        @JsonProperty("file_sources")
        List<String> filesources
) {
}
