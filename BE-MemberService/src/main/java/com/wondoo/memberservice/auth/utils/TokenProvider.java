package com.wondoo.memberservice.auth.utils;

import com.wondoo.memberservice.auth.data.request.RefreshRelatedRequest;
import com.wondoo.memberservice.auth.data.response.LoginTokenResponse;
import com.wondoo.memberservice.auth.data.response.RefreshTokenResponse;
import com.wondoo.memberservice.auth.data.response.TokenResponse;
import com.wondoo.memberservice.auth.exception.AuthErrorCode;
import com.wondoo.memberservice.auth.exception.AuthException;
import com.wondoo.memberservice.member.domain.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenProvider {

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.access.expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.token.refresh.expiration}")
    private long refreshTokenExpiration;

    private final RedisTemplate<String, String> redisTemplate;

    /**
     * JWT 발급 및 Redis 백업
     *
     * @param memberId member_id를 claim 으로 등록
     * @return access_token 및 refresh_token
     */
    public TokenResponse jwtSave(Long memberId, Member member) {

        Claims claims = Jwts.claims()
                .setSubject(String.valueOf(memberId));
        Date now = new Date();
        Date accessExpireDate = new Date(now.getTime() + accessTokenExpiration);
        Date refreshExpireDate = new Date(now.getTime() + refreshTokenExpiration);
        Key hmacKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(accessExpireDate)
                .signWith(hmacKey, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(refreshExpireDate)
                .signWith(hmacKey, SignatureAlgorithm.HS256)
                .compact();

        // Redis 에 저장
        redisTemplate.opsForValue().set(
                String.valueOf(memberId),
                refreshToken,
                refreshTokenExpiration,
                TimeUnit.MILLISECONDS
        );

        if (member != null) {
            return LoginTokenResponse.builder()
                    .memberId(memberId)
                    .imageId(member.getImageId())
                    .nickname(member.getNickname())
                    .name(member.getName())
                    .email(member.getEmail())
                    .phone(member.getPhone())
                    .gender(member.getGender())
                    .level(member.getLevel())
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        }

        return RefreshTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void checkRefreshToken(Long memberId, RefreshRelatedRequest refreshRelatedRequest) {
        try {
            String secretKey = Base64.getEncoder().encodeToString(secret.getBytes());
            Key hmacKey = new SecretKeySpec(Base64.getDecoder().decode(secretKey), SignatureAlgorithm.HS256.getJcaName());

            String checkMemberId = Jwts.parserBuilder()
                    .setSigningKey(hmacKey)
                    .build()
                    .parseClaimsJws(refreshRelatedRequest.refreshToken())
                    .getBody().getSubject();

            if (!checkMemberId.equals(String.valueOf(memberId))) {
                throw new AuthException(AuthErrorCode.REFRESH_NOT_VALID);
            }

            redisTemplate.delete(String.valueOf(memberId));
        } catch (JwtException e) {
            throw new AuthException(AuthErrorCode.REFRESH_NOT_VALID);
        }
    }
}
