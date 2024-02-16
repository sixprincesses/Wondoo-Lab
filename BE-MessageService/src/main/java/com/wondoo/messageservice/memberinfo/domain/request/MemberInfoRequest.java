package com.wondoo.messageservice.memberinfo.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.JoinColumn;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberInfoRequest {
    @JsonProperty("member_id")
    private Long memberId;
    @JsonProperty("nickname")
    private String nickname;
    @JsonProperty("image_url")
    private String imageurl;
}
