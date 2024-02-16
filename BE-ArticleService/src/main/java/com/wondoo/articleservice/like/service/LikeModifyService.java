package com.wondoo.articleservice.like.service;

import com.wondoo.articleservice.like.data.request.LikeDeleteRequest;
import com.wondoo.articleservice.like.data.request.LikeSaveRequest;
import com.wondoo.articleservice.like.data.response.LikeDeleteResponse;
import com.wondoo.articleservice.like.data.response.LikeSaveResponse;

public interface LikeModifyService {
    LikeSaveResponse save(Long memberId, LikeSaveRequest request);

    LikeDeleteResponse delete(Long memberId, LikeDeleteRequest request);
}
