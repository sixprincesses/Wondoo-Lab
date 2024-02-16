package com.wondoo.articleservice.feed.domain;

import com.wondoo.articleservice.memberinfo.domain.MemberInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FeedInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reference;
    private String title;

    @JoinColumn(name = "member_info_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private MemberInfo memberInfo;

    @Builder
    public FeedInfo(String reference, String title, MemberInfo memberInfo) {
        this.reference = reference;
        this.title = title;
        this.memberInfo = memberInfo;
    }

    public void updateTitle(String title) {
        this.title = title;
    }
}
