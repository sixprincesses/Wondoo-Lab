package com.wondoo.memberservice.member.service;

import com.wondoo.memberservice.member.data.response.MemberDetailResponse;
import com.wondoo.memberservice.member.data.response.MemberRankingResponse;
import com.wondoo.memberservice.member.data.response.MemberSearchResponse;

public interface MemberLoadService {

    MemberDetailResponse memberDetailLoad(Long memberId, Long checkId);
    MemberSearchResponse memberSearchLoad(String keyword);

    MemberRankingResponse memberRankingLoad();
}
