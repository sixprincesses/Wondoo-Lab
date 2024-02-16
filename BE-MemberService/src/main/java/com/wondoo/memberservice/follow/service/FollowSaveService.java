package com.wondoo.memberservice.follow.service;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface FollowSaveService {

    void memberFollow(Long memberId, Long socialId);

    void memberUnfollow(Long memberId, Long socialId);
}
