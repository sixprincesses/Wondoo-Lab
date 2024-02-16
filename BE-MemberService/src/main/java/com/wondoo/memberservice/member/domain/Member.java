package com.wondoo.memberservice.member.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wondoo.memberservice.follow.domain.Follow;
import com.wondoo.memberservice.global.domain.BaseEntity;
import com.wondoo.memberservice.point.domain.Point;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @NotNull
    @Column(name = "social_id", unique = true)
    private Long socialId;

    @NotBlank
    @Column(name = "social_nickname", nullable = false)
    private String socialNickname;

    @Column(name = "image_id")
    private String imageId;

    @Column(name = "cover_image_id")
    private String coverImageId;

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

    @Column(name = "openai_token")
    private String openaiToken;

    @Column(name = "github_token")
    private String githubToken;

    @JsonIgnore
    @OneToMany(mappedBy = "to")
    private List<Follow> followings = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "from")
    private List<Follow> followers = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "member")
    private List<Attendance> attendances = new ArrayList<>();

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "statistic_id")
    private Statistic statistic;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "point_id")
    private Point point;

    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    private Level level;

    @Column(name = "target_time")
    private Long targetTime;

    @Builder
    public Member(Long socialId, String socialNickname, Statistic statistic, Point point) {
        this.socialId = socialId;
        this.socialNickname = socialNickname;
        this.nickname = socialNickname;
        this.statistic = statistic;
        this.point = point;
        level = Level.BEAN_1;
        targetTime = 4L;
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

    public void updateLevel(Level newLevel) {
        level = newLevel;
    }

    public void updateTargetTime(Long newTargetTime) {
        targetTime = newTargetTime;
    }

    public void updateOpenaiToken(String newOpenaiToken) {
        openaiToken = newOpenaiToken;
    }

    public void updateGithubToken(String newGithubToken) {
        githubToken = newGithubToken;
    }

    public void updateImage(String newImageId) {
        imageId = newImageId;
    }

    public void updateCoverImage(String newCoverImageId) {
        coverImageId = newCoverImageId;
    }
}
