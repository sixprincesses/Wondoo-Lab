package com.wondoo.articleservice.feed.data.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ArticleUpdateMessage(
        @JsonProperty("member_id")
        Long memberId,
        @JsonProperty("date")
        LocalDate date
) {
}
