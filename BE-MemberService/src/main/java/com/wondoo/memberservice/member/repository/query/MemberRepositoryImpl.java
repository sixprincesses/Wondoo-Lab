package com.wondoo.memberservice.member.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wondoo.memberservice.member.data.query.MemberSearchQuery;
import com.wondoo.memberservice.member.data.query.QMemberSearchQuery;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.wondoo.memberservice.member.domain.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MemberSearchQuery> memberSearch(String keyword) {
        return queryFactory
                .select(
                        new QMemberSearchQuery(
                                member.id,
                                member.nickname,
                                member.imageId,
                                member.level
                        )
                )
                .from(member)
                .where(member.nickname.contains(keyword))
                .orderBy(member.id.asc())
                .limit(9)
                .fetch();
    }
}
