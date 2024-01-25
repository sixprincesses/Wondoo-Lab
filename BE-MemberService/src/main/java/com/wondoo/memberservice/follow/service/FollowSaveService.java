package com.wondoo.memberservice.follow.service;

public interface FollowSaveService {

    void memberFollow(Long memberId, Long socialId);

    void memberUnfollow(Long memberId, Long socialId);
}
