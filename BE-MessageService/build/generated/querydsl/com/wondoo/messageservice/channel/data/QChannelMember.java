package com.wondoo.messageservice.channel.data;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QChannelMember is a Querydsl query type for ChannelMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChannelMember extends EntityPathBase<ChannelMember> {

    private static final long serialVersionUID = -497491503L;

    public static final QChannelMember channelMember = new QChannelMember("channelMember");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final StringPath reference = createString("reference");

    public QChannelMember(String variable) {
        super(ChannelMember.class, forVariable(variable));
    }

    public QChannelMember(Path<? extends ChannelMember> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChannelMember(PathMetadata metadata) {
        super(ChannelMember.class, metadata);
    }

}

