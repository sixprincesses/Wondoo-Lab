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

    @Value("${test.token}")
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

    @DisplayName("value 를 추출한다")
    @Test
    void getSubjectByTokenTest(@Value("${test.id}") String expected) {
        /* Given */
        /* When */
        String value = jwtUtils.getSubjectByJwt(token);
        System.out.println(expected);

        /* Then */
        assertThat(value).isEqualTo(expected);
    }
}