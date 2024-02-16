package com.wondoo.memberservice.follow.repository.query;

import com.wondoo.memberservice.follow.data.query.FollowsResponse;
import com.wondoo.memberservice.follow.domain.Follow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface FollowRepositoryCustom {

    Page<FollowsResponse> followersLoad(Long memberId, Pageable pageable);
    Page<FollowsResponse> followingsLoad(Long memberId, Pageable pageable);
    Optional<Follow> findByFollow(Long toId, Long fromId);
    Page<FollowsResponse> friendsLoad(Long memberId, Pageable pageable);
}
