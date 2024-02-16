package com.wondoo.articleservice.bookmark.domain;

import com.wondoo.articleservice._global.domain.BaseEntity;
import com.wondoo.articleservice.feed.domain.FeedInfo;
import com.wondoo.articleservice.memberinfo.domain.MemberInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Bookmark extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;

    @JoinColumn(name = "member_info_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private MemberInfo memberInfo;

    @JoinColumn(name = "feed_info_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private FeedInfo feedinfo;

    @Builder
    public Bookmark(Long id, MemberInfo memberInfo, FeedInfo feedinfo) {
        this.id = id;
        this.memberInfo = memberInfo;
        this.feedinfo = feedinfo;
    }
}
