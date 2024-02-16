package com.wondoo.messageservice.message.repository.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wondoo.messageservice.message.data.Message;
import lombok.RequiredArgsConstructor;

import static com.wondoo.messageservice.message.data.QMessage.*;

@RequiredArgsConstructor
public class MessageRepositoryCustomImpl implements MessageRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public Message findLastMessageByReference(String reference){
        return jpaQueryFactory.selectFrom(message)
                .where(message.reference.eq(reference))
                .orderBy(message.createdTime.asc())
                .fetchFirst();
    }
}
