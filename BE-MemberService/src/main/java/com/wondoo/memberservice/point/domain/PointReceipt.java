package com.wondoo.memberservice.point.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Document(collection = "point_receipt")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointReceipt {

    @Id
    private String id;

    @Field("member_id")
    private Long memberId;

    @Field("type")
    private String type;

    @Field("point")
    private Integer point;

    @Field("time")
    private Long time;

    @Builder
    public PointReceipt(Long memberId, String type, Integer point, Long time) {
        this.memberId = memberId;
        this.type = type;
        this.point = point;
        this.time = time;
    }
}
