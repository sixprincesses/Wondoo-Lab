package com.wondoo.memberservice.member.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wondoo.memberservice.member.data.query.MemberSearchQuery;
import lombok.Builder;

import java.util.List;

@Builder
public record MemberSearchResponse(
        @JsonProperty("search_members")
        List<MemberSearchQuery> memberSearchQueries
) {
}
