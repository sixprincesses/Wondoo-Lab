package com.wondoo.articleservice.memberinfo.service;

import com.wondoo.articleservice.memberinfo.data.request.MemberInfoRequest;
import com.wondoo.articleservice.memberinfo.domain.MemberInfo;
import com.wondoo.articleservice.memberinfo.repository.MemberInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberInfoService {
    private final MemberInfoRepository memberInfoRepository;

    @Transactional
    public void updateMemberInfo(MemberInfoRequest request) {
        log.info("UpdateMemberInfo Request MemberId : [{}]", request.memberId());
        Optional<MemberInfo> memberInfo = memberInfoRepository.findById(request.memberId());
        if (memberInfo.isEmpty()) {
            log.info("Member Empty");
            memberInfoRepository.save(MemberInfo.builder()
                    .id(request.memberId())
                    .nickname(request.nickname())
                    .imageurl(request.imageurl())
                    .level(request.level())
                    .build());
            return;
        }
        memberInfo.get().updateMemberInfo(request.nickname(), request.imageurl(), request.level());
        log.info("Member Update Info Kafka End MemberId : [{}]", memberInfo.get().getId());
    }
}
