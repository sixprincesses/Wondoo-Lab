package com.wondoo.articleservice.feed.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedMember {
    @Id
    @GeneratedValue
    private Long id;

    private Long memberId;
    private String feedId;
}
