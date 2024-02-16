package com.wondoo.messageservice.memberinfo.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static com.wondoo.messageservice.memberinfo.data.QMemberInfo.*;

@RequiredArgsConstructor
public class MemberInfoRepositoryCustomImpl implements MemberInfoRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public String findNicknameById(Long memberId) {
        return jpaQueryFactory.select(memberInfo.nickname)
                .from(memberInfo)
                .where(memberInfo.id.eq(memberId))
                .fetchFirst();
    }

    @Override
    public String findImageurlById(Long memberId) {
        return jpaQueryFactory.select(memberInfo.imageurl)
                .from(memberInfo)
                .where(memberInfo.id.eq(memberId))
                .fetchFirst();
    }
}
