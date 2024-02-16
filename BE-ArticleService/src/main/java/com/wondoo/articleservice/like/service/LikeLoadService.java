package com.wondoo.articleservice.like.service;

import com.wondoo.articleservice.like.data.request.LikesRequest;
import com.wondoo.articleservice.like.data.response.LikesResponse;

public interface LikeLoadService {
    /**
     * {@link LikesResponse}에서 `lastLikeId`이 `-1`인 경우에는 더 이상 데이터가 없다는 뜻입니다.
     */
    LikesResponse findLikes(Long memberId, LikesRequest request);
}
