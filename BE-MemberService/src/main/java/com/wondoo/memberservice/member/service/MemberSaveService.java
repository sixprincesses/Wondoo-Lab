package com.wondoo.memberservice.member.service;

import com.wondoo.memberservice.member.data.request.MemberUpdateRequest;

public interface MemberSaveService {

    void memberUpdate(Long memberId, Long socialId, MemberUpdateRequest memberUpdateRequest);
}
