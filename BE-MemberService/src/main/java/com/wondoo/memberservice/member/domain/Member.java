package com.wondoo.memberservice.member.domain;

import com.wondoo.memberservice.global.domain.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member extends BaseEntity {

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

    @Pattern(regexp = "^[가-힣A-Za-z][가-힣A-Za-z0-9]{1,9}$")
    @Column(name = "nickname")
    private String nickname;

    @Column(name = "name")
    private String name;

    @Email
    @Column(name = "email")
    private String email;

    @Pattern(regexp = "^[0-9]{11}$")
    @Column(name = "phone")
    private String phone;

    @Column(name = "gender")
    private String gender;

    @Builder
    public Member(Long socialId, String socialNickname) {
        this.socialId = socialId;
        this.socialNickname = socialNickname;
        this.nickname = socialNickname;
    }

    public void updateSocialNickname(String newSocialNickname) {
        socialNickname = newSocialNickname;
    }

    public void updateMemberInfo(String newNickname, String newName, String newEmail, String newPhone, String newGender){
        nickname = newNickname;
        name = newName;
        email = newEmail;
        phone = newPhone;
        gender = newGender;
    }
}
