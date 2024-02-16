package com.wondoo.articleservice.keyword.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class KeywordRank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(
            mappedBy = "keywordRank",
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "keyword_id")
    private Keyword keyword;

    @Column(name = "prev_count")
    private Long prevCount;
    @Column(name = "curr_count")
    private Long currCount;

    @Builder
    public KeywordRank(Keyword keyword, Long prevCount, Long currCount) {
        this.keyword = keyword;
        this.prevCount = prevCount;
        this.currCount = currCount;
    }

    public void updateKeywordRank(Long prevCount, Long currCount) {
        this.prevCount = prevCount;
        this.currCount = currCount;
    }
}
