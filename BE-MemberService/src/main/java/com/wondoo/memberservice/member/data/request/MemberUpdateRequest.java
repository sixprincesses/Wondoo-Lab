package com.wondoo.memberservice.member.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

@Builder
public record MemberUpdateRequest(
        @Pattern(regexp = "^[가-힣A-Za-z][가-힣A-Za-z0-9]{1,9}$",
                message = "닉네임은 특수문자를 포함하지 않은 2-10자리이며, " +
                        "첫글자는 한글 또는 영어로 작성해야 합니다.")
        @JsonProperty("nickname")
        String nickname,
        @JsonProperty("name")
        String name,
        @Email
        @JsonProperty("email")
        String email,
        @Pattern(regexp = "^[0-9]{11}$",
                message = "전화번호는 11자리의 숫자로 이루어져야 합니다.")
        @JsonProperty("phone")
        String phone,
        @JsonProperty("gender")
        String gender
) {
}
