package com.wondoo.memberservice.member.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @NotNull
    @Column(name = "social_id", unique = true)
    private Long socialId;

    @NotBlank
    @Column(name = "social_nickname", nullable = false)
    private String socialNickname;

    @Builder
    public Member(Long socialId, String socialNickname) {
        this.socialId = socialId;
        this.socialNickname = socialNickname;
    }

    public void updateSocialNickname(String socialNickname){
        this.socialNickname = socialNickname;
    }
}
