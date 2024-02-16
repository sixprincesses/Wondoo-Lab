package com.wondoo.articleservice.keyword.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Keyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", unique = true)
    private String name;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private KeywordRank keywordRank;

    @Builder
    public Keyword(String name) {
        this.name = name;
    }

    public void updateKeywordRank(KeywordRank keywordRank){
        this.keywordRank = keywordRank;
    }
}
