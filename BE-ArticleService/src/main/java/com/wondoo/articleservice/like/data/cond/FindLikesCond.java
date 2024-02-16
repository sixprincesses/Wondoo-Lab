package com.wondoo.articleservice.like.data.cond;

import com.wondoo.articleservice.like.data.request.LikesRequest;
import lombok.Builder;
import org.springframework.data.domain.Sort;

@Builder
public record FindLikesCond(
        Long memberId,
        Long lastLikeId,
        int pageSize,
        Sort.Direction direction
) {
    public static FindLikesCond of(Long memberId, LikesRequest request) {
        return FindLikesCond.builder()
                .memberId(memberId)
                .lastLikeId(request.last())
                .pageSize(request.size())
                .direction(request.dir())
                .build();
    }
}
