package com.wondoo.memberservice.diary.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wondoo.memberservice.diary.domain.CommitDetail;
import lombok.Builder;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Builder
public record DiarySaveResponse(
        @JsonProperty("diary_id")
        String id,

        @JsonProperty("member_id")
        Long memberId,

        @JsonProperty("content")
        String content,

        @JsonProperty("date")
        LocalDate date,

        @JsonProperty("repository")
        Set<String> repository,

        @JsonProperty("commit_count")
        Integer commitCount,

        @JsonProperty("log")
        Map<String, List<CommitDetail>> log
) {
}
