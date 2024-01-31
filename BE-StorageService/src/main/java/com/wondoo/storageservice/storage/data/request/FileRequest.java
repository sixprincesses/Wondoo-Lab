package com.wondoo.storageservice.storage.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FileRequest(
        @JsonProperty("filesource")
        String filesource
) {
}
