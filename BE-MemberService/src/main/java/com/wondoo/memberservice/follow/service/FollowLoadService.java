package com.wondoo.memberservice.follow.service;

import com.wondoo.memberservice.follow.data.query.FollowsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FollowLoadService {

    Page<FollowsResponse> followersLoad(Long memberId, Pageable pageable);
    Page<FollowsResponse> followingsLoad(Long memberId, Pageable pageable);
}
