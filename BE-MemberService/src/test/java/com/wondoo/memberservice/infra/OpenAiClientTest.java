package com.wondoo.memberservice.infra;

import com.wondoo.memberservice.global.annotation.WondooBootTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

@WondooBootTest
class OpenAiClientTest {

    @Autowired
    private OpenAiClient client;

    @DisplayName("OPENAI 연결 테스트")
    @Nested
    class GetStatusCodeByConnection {
        @DisplayName("유효한 API Key 인 경우 연결에 성공한다.")
        @Test
        void connectionSuccess(@Value("${openai-token}") String token) {
            /* Given */
            /* When */
            int actual = client.getStatusCodeByConnection(token);

            /* Then */
            assertThat(actual).isEqualTo(HttpStatus.OK.value());
        }

        @DisplayName("유효하지 않은 API Key 인 경우 연결에 실패한다.")
        @Test
        void connectionFailed() throws MalformedURLException, URISyntaxException {
            /* Given */
            /* When */
            int actual = client.getStatusCodeByConnection("dummyToken");

            /* Then */
            assertThat(actual).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        }
    }
}