package com.wondoo.articleservice.like.data.request;

import static org.springframework.data.domain.Sort.Direction;

public record LikesRequest(
        Long last,
        int size,
        Direction dir
) {
}
