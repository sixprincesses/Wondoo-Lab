package com.wondoo.apigateway.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JwtUtilsTest {

    @Autowired
    private JwtUtils jwtUtils;

    @Value("${secret.token}")
    private String token;

    @DisplayName("JWT의 유효성을 검증한다")
    @Test
    void validateJwtTest() {
        /* Given */
        /* When */
        boolean valid = jwtUtils.validateJwt(token);

        /* Then */
        assertThat(valid).isFalse();
    }

    @DisplayName("socialId를 추출한다")
    @Test
    void getSubjectByTokenTest() {
        /* Given */
        /* When */
        String socialId = jwtUtils.getSubjectByJwt(token);

        /* Then */
        assertThat(socialId).isEqualTo("116432941");
    }
}