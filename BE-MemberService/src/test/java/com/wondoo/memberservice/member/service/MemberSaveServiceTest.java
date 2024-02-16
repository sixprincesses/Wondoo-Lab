package com.wondoo.memberservice.member.service;

import com.wondoo.memberservice.global.annotation.WondooMockTest;
import com.wondoo.memberservice.infra.OpenAiClient;
import com.wondoo.memberservice.member.data.request.TokenUpdateRequest;
import com.wondoo.memberservice.member.domain.Member;
import com.wondoo.memberservice.member.exception.MemberErrorCode;
import com.wondoo.memberservice.member.exception.MemberException;
import com.wondoo.memberservice.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@WondooMockTest
class MemberSaveServiceTest {
    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private OpenAiClient openaiClient;

    @DisplayName("OPENAI TOKEN 검증 테스트")
    @Nested
    class updateOpenaiToken {
        private static final Long MOCK_REQUEST_MEMBER_ID = 1L;
        private static final TokenUpdateRequest MOCK_OPENAI_TOKEN_UPDATE_REQUEST
                = new TokenUpdateRequest("token");
        private Member member;

        @BeforeEach
        void setup() {
            member = Member.builder().build();
        }

        @DisplayName("OPENAI TOKEN 검증에 성공한다")
        @Test
        void validateSuccess() {
            /* Given */
            given(memberRepository.findById(MOCK_REQUEST_MEMBER_ID))
                    .willReturn(Optional.of(member));
            given(openaiClient.getStatusCodeByConnection(MOCK_OPENAI_TOKEN_UPDATE_REQUEST.token()))
                    .willReturn(HttpStatus.OK.value());

            /* When */
            /* Then */
            assertThatCode(() -> memberService.updateOpenaiToken(
                    MOCK_REQUEST_MEMBER_ID,
                    MOCK_OPENAI_TOKEN_UPDATE_REQUEST))
                    .doesNotThrowAnyException();
        }

        @DisplayName("OPENAI TOKEN 검증에 실패한다")
        @Test
        void validateFailed() {
            /* Given */
            given(memberRepository.findById(MOCK_REQUEST_MEMBER_ID))
                    .willReturn(Optional.of(member));
            given(openaiClient.getStatusCodeByConnection(MOCK_OPENAI_TOKEN_UPDATE_REQUEST.token()))
                    .willReturn(HttpStatus.UNAUTHORIZED.value());

            /* When */
            /* Then */
            assertThatThrownBy(() -> memberService.updateOpenaiToken(
                    MOCK_REQUEST_MEMBER_ID,
                    MOCK_OPENAI_TOKEN_UPDATE_REQUEST))
                    .isInstanceOf(MemberException.class)
                    .hasMessage(MemberErrorCode.INVALID_OPENAI_TOKEN.getMessage());
        }
    }
}