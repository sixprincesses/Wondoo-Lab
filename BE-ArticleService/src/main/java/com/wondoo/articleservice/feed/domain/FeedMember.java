package com.wondoo.articleservice.feed.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedMember {
    @Id
    @GeneratedValue
    private Long id;

    private Long memberId;
    private String feedId;
}
