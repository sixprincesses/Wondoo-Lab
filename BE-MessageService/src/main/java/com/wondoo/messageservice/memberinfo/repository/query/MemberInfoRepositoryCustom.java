package com.wondoo.messageservice.memberinfo.repository.query;

public interface MemberInfoRepositoryCustom {
    String findNicknameById(Long memberId);
    String findImageurlById(Long memberId);
}
