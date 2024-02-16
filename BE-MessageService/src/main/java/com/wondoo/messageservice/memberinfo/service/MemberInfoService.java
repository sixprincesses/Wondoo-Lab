package com.wondoo.messageservice.memberinfo.service;

import com.wondoo.messageservice._global.data.MemberInfoEventRequest;
import com.wondoo.messageservice.memberinfo.data.MemberInfo;
import com.wondoo.messageservice.memberinfo.domain.request.MemberInfoRequest;
import com.wondoo.messageservice.memberinfo.repository.MemberInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberInfoService {
    private final MemberInfoRepository memberInfoRepository;

    public String findNickname(Long memberId) {
        return memberInfoRepository.findNicknameById(memberId);
    }

    @Transactional
    public void updateMemberInfo(MemberInfoEventRequest request) {
        Optional<MemberInfo> memberInfo = memberInfoRepository.findById(request.memberId());
        if (memberInfo.isEmpty()) {
            memberInfoRepository.save(MemberInfo.builder()
                    .id(request.memberId())
                    .nickname(request.nickname())
                    .imageurl(request.imageurl())
                    .build());
            return;
        }
        memberInfo.get().updateMemberInfo(request.nickname(), request.imageurl());
    }
}
