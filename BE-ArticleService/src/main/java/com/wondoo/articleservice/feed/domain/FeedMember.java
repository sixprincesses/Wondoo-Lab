package com.wondoo.articleservice.feed.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedMember {
    @Id
    @GeneratedValue
    private Long id;

    private Long memberId;
    private String feedId;

    @Builder
    public FeedMember(Long memberId, String feedId) {
        this.memberId = memberId;
        this.feedId = feedId;
    }
}
