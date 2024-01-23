package com.wondoo.memberservice.auth.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "refresh_token", timeToLive = 60 * 60 * 24 * 7)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    private Long socialId;
    private String refreshToken;

    @Builder
    public RefreshToken(Long socialId, String refreshToken) {
        this.socialId = socialId;
        this.refreshToken = refreshToken;
    }
}
