package com.wondoo.memberservice.member.service;

import com.wondoo.memberservice.member.data.response.MemberDetailResponse;

public interface MemberLoadService {

    MemberDetailResponse memberLoad(Long memberId);
}
