package com.wondoo.memberservice.member.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "statistic")
public class Statistic {

    @Id
    @GeneratedValue
    @Column(name = "statistic_id")
    private Long id;

    @OneToOne(
            mappedBy = "statistic",
            fetch = FetchType.LAZY
    )
    private Member member;

    @Min(0)
    @Column(name = "follower_count")
    private Long followerCount;

    @Min(0)
    @Column(name = "following_count")
    private Long followingCount;

    @Builder
    public Statistic() {
        this.followerCount = 0L;
        this.followingCount = 0L;
    }

    public void followerCalculate(boolean isFollowed) {

        if (isFollowed) {
            followerCount += 1;
        } else {
            if (followerCount > 0) {
                followerCount -= 1;
            }
        }
    }

    public void followingCalculate(boolean isFollowed) {

        if (isFollowed) {
            followingCount += 1;
        } else {
            if (followingCount > 0) {
                followingCount -= 1;
            }
        }
    }
}



