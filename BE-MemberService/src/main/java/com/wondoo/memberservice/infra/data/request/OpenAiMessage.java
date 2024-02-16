package com.wondoo.memberservice.infra.data.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class OpenAiMessage {
    @JsonProperty("role")
    private String role = "user";

    @JsonProperty("content")
    private String content;

    private OpenAiMessage(String content) {
        this.content = content;
    }

    public static OpenAiMessage makeConstantConnectionMessage() {
        return new OpenAiMessage("HI");
    }

    /* TODO: Prompt 정적 생성자 필요 */
    public static OpenAiMessage makePromptMessage(String content) {
        return new OpenAiMessage(content);
    }
}
