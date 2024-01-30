package com.wondoo.memberservice.follow.service;

import com.wondoo.memberservice.follow.data.query.FollowersResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FollowLoadService {

    Page<FollowersResponse> followersLoad(Long memberId, Pageable pageable);
}
