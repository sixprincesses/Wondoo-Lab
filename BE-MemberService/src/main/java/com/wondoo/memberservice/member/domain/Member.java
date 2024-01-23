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

    @Column(name = "image_id")
    private Long imageId;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "gender")
    private String gender;

    @Builder
    public Member(Long socialId, String socialNickname) {
        this.socialId = socialId;
        this.socialNickname = socialNickname;
    }

    public void updateSocialNickname(String socialNickname) {
        this.socialNickname = socialNickname;
    }
}
