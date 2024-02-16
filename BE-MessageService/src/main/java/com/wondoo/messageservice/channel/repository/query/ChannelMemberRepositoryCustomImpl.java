package com.wondoo.messageservice.channel.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.wondoo.messageservice.channel.data.QChannelMember.*;


@RequiredArgsConstructor
public class ChannelMemberRepositoryCustomImpl implements ChannelMemberRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long findMemberIdByMemberIdAndReference(Long memberId, String reference) {
        return jpaQueryFactory.select(channelMember.memberId)
                .from(channelMember)
                .where(channelMember.memberId.ne(memberId).and(channelMember.reference.eq(reference)))
                .fetchFirst();
    }

    @Override
    public List<String> findByMemberId(Long memberId) {
        return jpaQueryFactory.select(channelMember.reference)
                .from(channelMember)
                .where(channelMember.memberId.eq(memberId))
                .fetch();
    }
}
