package com.wondoo.messageservice.memberinfo.data;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMemberInfo is a Querydsl query type for MemberInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberInfo extends EntityPathBase<MemberInfo> {

    private static final long serialVersionUID = 394608787L;

    public static final QMemberInfo memberInfo = new QMemberInfo("memberInfo");

    public final DateTimePath<java.time.LocalDateTime> createdTime = createDateTime("createdTime", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageurl = createString("imageurl");

    public final StringPath nickname = createString("nickname");

    public final DateTimePath<java.time.LocalDateTime> updatedTime = createDateTime("updatedTime", java.time.LocalDateTime.class);

    public QMemberInfo(String variable) {
        super(MemberInfo.class, forVariable(variable));
    }

    public QMemberInfo(Path<? extends MemberInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMemberInfo(PathMetadata metadata) {
        super(MemberInfo.class, metadata);
    }

}

