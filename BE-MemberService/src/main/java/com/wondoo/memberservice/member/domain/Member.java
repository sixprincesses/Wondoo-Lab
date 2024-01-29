package com.wondoo.memberservice.member.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wondoo.memberservice.follow.domain.Follow;
import com.wondoo.memberservice.global.domain.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @Pattern(regexp = "^[가-힣A-Za-z][가-힣A-Za-z0-9-]{1,40}$")
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

    @JsonIgnore
    @OneToMany(mappedBy = "to")
    private List<Follow> followings = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "from")
    private List<Follow> followers = new ArrayList<>();

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "statistic_id")
    private Statistic statistic;

    @Builder
    public Member(Long socialId, String socialNickname, Statistic statistic) {
        this.socialId = socialId;
        this.socialNickname = socialNickname;
        this.nickname = socialNickname;
        this.statistic = statistic;
    }

    public void updateSocialNickname(String newSocialNickname) {
        socialNickname = newSocialNickname;
    }

    public void updateMemberInfo(String newNickname, String newName, String newEmail, String newPhone, String newGender) {
        nickname = (newNickname != null ? newNickname : nickname);
        name = (newName != null ? newName : name);
        email = (newEmail != null ? newEmail : email);
        phone = (newPhone != null ? newPhone : phone);
        gender = (newGender != null ? newGender : gender);
    }
}
