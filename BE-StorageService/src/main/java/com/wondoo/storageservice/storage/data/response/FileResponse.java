package com.wondoo.storageservice.storage.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record FileResponse(
        @JsonProperty("file_source")
        String filesource
) {
}
