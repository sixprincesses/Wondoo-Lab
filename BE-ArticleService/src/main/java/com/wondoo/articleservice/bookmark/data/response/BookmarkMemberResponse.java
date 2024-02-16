package com.wondoo.articleservice.bookmark.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wondoo.articleservice.memberinfo.domain.MemberInfo;
import lombok.Builder;

@Builder
public record BookmarkMemberResponse(
        @JsonProperty("member_id")
        Long memberId,
        @JsonProperty("image_id")
        String memberImage,
        @JsonProperty("nickname")
        String memberNickname
) {

    public static BookmarkMemberResponse from(MemberInfo memberInfo) {
        return BookmarkMemberResponse.builder()
                .memberId(memberInfo.getId())
                .memberImage(memberInfo.getImageurl())
                .memberNickname(memberInfo.getNickname())
                .build();
    }
}
