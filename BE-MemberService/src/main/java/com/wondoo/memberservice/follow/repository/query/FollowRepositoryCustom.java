package com.wondoo.memberservice.follow.repository.query;

import com.wondoo.memberservice.follow.data.query.FollowersResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FollowRepositoryCustom {

    Page<FollowersResponse> followersLoad(Long memberId, Pageable pageable);
}
