package com.wondoo.memberservice.point.domain;

import com.wondoo.memberservice.member.domain.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Table(name = "point")
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "point_id")
    private Long id;

    @OneToOne(
            mappedBy = "point",
            fetch = FetchType.LAZY
    )
    private Member member;

    @Min(0)
    @Column(name = "point")
    private Integer point;

    @Builder
    public Point() {
        this.point = 0;
    }

    public void pointUpdate(Integer newPoint) {
        point += newPoint;
    }
}
