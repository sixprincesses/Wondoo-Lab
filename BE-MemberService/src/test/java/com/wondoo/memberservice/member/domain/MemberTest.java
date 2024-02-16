package com.wondoo.memberservice.member.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MemberTest {

    @DisplayName("openai token 테스트")
    @Nested
    class OpenaiTokenTest {
        @DisplayName("OPENAI TOKEN 변경에 성공한다")
        @ParameterizedTest
        @ValueSource(strings = {"token"})
        void updateSuccessTest(String token) {
            /* Given */
            Member member = new Member();

            /* When */
            member.updateOpenaiToken(token);

            /* Then */
            Assertions.assertThat(member.getOpenaiToken()).isEqualTo(token);
        }
    }
}