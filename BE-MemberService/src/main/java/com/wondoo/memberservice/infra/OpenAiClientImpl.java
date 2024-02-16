package com.wondoo.memberservice.infra;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.wondoo.memberservice.global.utils.KafkaProducer;
import com.wondoo.memberservice.infra.data.request.OpenAiRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URL;

@RequiredArgsConstructor
@Component
public class OpenAiClientImpl implements OpenAiClient {

    public static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    private final RestTemplate restTemplate;
    private final KafkaProducer kafkaProducer;

    @Override
    @SneakyThrows
    public int getStatusCodeByConnection(String token) {
        try {
            ResponseEntity<String> exchange = restTemplate.exchange(
                    new URL(OPENAI_API_URL).toURI(),
                    HttpMethod.POST,
                    makeConnectionHttpEntity(token),
                    String.class
            );
            return exchange.getStatusCode().value();
        } catch (HttpClientErrorException e) {
            return HttpStatus.UNAUTHORIZED.value();
        }
    }

    @Override
    public String diaryByPrompt(Long memberId, String token, String content) {
        ResponseEntity<String> exchange = restTemplate.exchange(
                OPENAI_API_URL,
                HttpMethod.POST,
                makePromptHttpEntity(token, content),
                String.class
        );

        if (exchange.getStatusCode().value() != 200) {
            kafkaProducer.sendDiaryMessage(String.valueOf(memberId), content);
            return null;
        }

        JsonObject messageObject = getMessageObject(exchange);

        return messageObject.get("content").getAsString();
    }

    private static JsonObject getMessageObject(ResponseEntity<String> exchange) {
        Gson gson = new Gson();
        JsonElement responseElement = gson.fromJson(exchange.getBody(), JsonElement.class);
        JsonObject responseJson = responseElement.getAsJsonObject();
        JsonArray choicesArray = responseJson.getAsJsonArray("choices");
        return choicesArray.get(0).getAsJsonObject().getAsJsonObject("message");
    }

    private HttpEntity<OpenAiRequest> makePromptHttpEntity(String token, String content) {
        return new HttpEntity<>(
                OpenAiRequest.makePromptBody(content),
                makeHeaders(token)
        );
    }

    private HttpEntity<OpenAiRequest> makeConnectionHttpEntity(String token) {
        return new HttpEntity<>(
                OpenAiRequest.makeConstantConnectionBody(),
                makeHeaders(token)
        );
    }

    private HttpHeaders makeHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + token);
        return headers;
    }
}
