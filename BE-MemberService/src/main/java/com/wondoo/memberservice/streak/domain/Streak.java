package com.wondoo.memberservice.streak.domain;

import com.wondoo.memberservice.member.domain.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Streak {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "streak_id")
    private Long id;

    @JoinColumn(name = "member_id")
    @ManyToOne
    private Member member;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "count")
    private int count;

    @Builder
    public Streak(Long id, Member member) {
        this.id = id;
        this.member = member;
        this.date = LocalDateTime.now().plusDays(1L).toLocalDate();
        this.count = 0;
    }

    public void changeCount(int i) {
        if (count + i < 0) {
            throw new RuntimeException("피드의 개수는 0개 미만일 수 없습니다.");
        }
        count += i;
    }

    public boolean canDelete() {
        return count == 0;
    }
}
