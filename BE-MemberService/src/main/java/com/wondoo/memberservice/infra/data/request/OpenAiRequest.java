package com.wondoo.memberservice.infra.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wondoo.memberservice.infra.data.GptModel;
import lombok.Getter;

import java.util.List;

@Getter
public class OpenAiRequest {
    @JsonProperty("model")
    private String model;

    @JsonProperty("messages")
    private List<OpenAiMessage> messages;

    private OpenAiRequest(GptModel model, List<OpenAiMessage> messages) {
        this.model = model.getName();
        this.messages = messages;
    }

    public static OpenAiRequest makeConstantConnectionBody() {
        return new OpenAiRequest(GptModel.GPT3, List.of(OpenAiMessage.makeConstantConnectionMessage()));
    }

    /* TODO: Prompt 정적 생성자 필요 */
    public static OpenAiRequest makePromptBody(String content) {
        return new OpenAiRequest(GptModel.GPT3, List.of(OpenAiMessage.makePromptMessage(content)));
    }
}
