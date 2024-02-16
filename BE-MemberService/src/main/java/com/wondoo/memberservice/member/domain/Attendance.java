package com.wondoo.memberservice.member.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "date")
    private LocalDate date;

    @Builder
    public Attendance(Member member, LocalDate date) {
        setMember(member);
        this.date = date;
    }

    public void setMember(Member member) {
        this.member = member;
        member.getAttendances().add(this);
    }
}
