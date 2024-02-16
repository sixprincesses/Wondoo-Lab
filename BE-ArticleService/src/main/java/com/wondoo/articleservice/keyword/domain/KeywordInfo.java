package com.wondoo.articleservice.keyword.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "keyword_info")
public class KeywordInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "keyword_id")
    private Keyword keyword;

    @Column(name = "member_id")
    private Long memberId;
    @Column(name = "feed_id")
    private Long feedId;

    @Builder
    public KeywordInfo(Keyword keyword, Long memberId, Long feedId) {
        this.keyword = keyword;
        this.memberId = memberId;
        this.feedId = feedId;
    }
}
