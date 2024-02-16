package com.wondoo.memberservice.member.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
public record MemberRankingResponse(
        @JsonProperty("member_ranking")
        List<MemberRankingInfoResponse> memberRanking
) {
}
