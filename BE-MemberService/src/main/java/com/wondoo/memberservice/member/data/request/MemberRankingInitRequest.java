package com.wondoo.memberservice.member.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wondoo.memberservice.member.domain.Level;
import lombok.Builder;

@Builder
public record MemberRankingInitRequest(
        @JsonProperty("member_id")
        Long memberId
) {
}
