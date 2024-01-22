package com.wondoo.memberservice.auth.utils;

import com.wondoo.memberservice.auth.data.request.MemberTokenRequest;
import com.wondoo.memberservice.auth.data.response.LoginTokenResponse;
import com.wondoo.memberservice.auth.data.response.SignupTokenResponse;
import com.wondoo.memberservice.auth.domain.RefreshToken;
import com.wondoo.memberservice.auth.repository.RefreshTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.access.expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.token.refresh.expiration}")
    private long refreshTokenExpiration;

    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * JWT 발급 및 Redis 백업
     * @param memberTokenRequest social_id를 claim 으로 등록
     * @return access_token 및 refresh_token
     */
    public LoginTokenResponse jwtSave(MemberTokenRequest memberTokenRequest){

        Claims claims = Jwts.claims()
                .setSubject(String.valueOf(memberTokenRequest.socialId()));
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
        refreshTokenRepository.save(RefreshToken.builder()
                .socialId(memberTokenRequest.socialId())
                .refreshToken(refreshToken)
                .build());

        return LoginTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
