package com.wondoo.articleservice.bookmark.data.request;

import static org.springframework.data.domain.Sort.Direction;

public record BookmarksRequest(
        Long last,
        int size,
        Direction dir
) {
}
