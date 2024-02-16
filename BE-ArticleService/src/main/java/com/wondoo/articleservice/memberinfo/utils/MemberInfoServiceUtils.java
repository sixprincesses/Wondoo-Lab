package com.wondoo.articleservice.memberinfo.utils;

import com.wondoo.articleservice.memberinfo.domain.MemberInfo;
import com.wondoo.articleservice.memberinfo.repository.MemberInfoRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.NoSuchElementException;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInfoServiceUtils {
    public static MemberInfo findById(MemberInfoRepository repository, Long memberId) {
        return repository.findById(memberId).orElseThrow(
                () -> new NoSuchElementException("해당 회원이 존재하지 않습니다.")
        );
    }
}
