package com.wondoo.memberservice.member.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ImageSaveResponse(
        @JsonProperty("file_source")
        String fileSource
) {
}
