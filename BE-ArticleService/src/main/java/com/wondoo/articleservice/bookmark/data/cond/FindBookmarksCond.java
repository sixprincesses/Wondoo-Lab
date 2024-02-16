package com.wondoo.articleservice.bookmark.data.cond;

import com.wondoo.articleservice.bookmark.data.request.BookmarksRequest;
import lombok.Builder;

import static org.springframework.data.domain.Sort.Direction;

@Builder
public record FindBookmarksCond(
        Long memberId,
        Long lastBookmarkId,
        int pageSize,
        Direction direction
) {
    public static FindBookmarksCond of(Long memberId, BookmarksRequest request) {
        return FindBookmarksCond.builder()
                .memberId(memberId)
                .lastBookmarkId(request.last())
                .pageSize(request.size())
                .direction(request.dir())
                .build();
    }
}
