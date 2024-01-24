package com.wondoo.memberservice.follow.domain;

import com.wondoo.memberservice.global.domain.BaseEntity;
import com.wondoo.memberservice.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "follow")
public class Follow extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id")
    private Member to;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id")
    private Member from;

    @Builder
    public Follow(Member to, Member from) {
        relateFollowerFollowing(to, from);
    }

    public void relateFollowerFollowing(Member to, Member from) {
        this.to = to;
        this.from = from;
        to.getFollowers().add(this);
        from.getFollowings().add(this);
    }
}
