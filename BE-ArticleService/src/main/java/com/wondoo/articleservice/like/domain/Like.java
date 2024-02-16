package com.wondoo.articleservice.like.domain;

import com.wondoo.articleservice._global.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "likes")
@Getter
@Entity
public class Like extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "feed_id")
    private Long feedId;

    @Builder
    public Like(Long id, Long memberId, Long feedId) {
        this.id = id;
        this.memberId = memberId;
        this.feedId = feedId;
    }
}
