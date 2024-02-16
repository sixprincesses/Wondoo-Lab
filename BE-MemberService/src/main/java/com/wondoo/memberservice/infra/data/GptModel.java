package com.wondoo.memberservice.infra.data;

import lombok.Getter;

@Getter
public enum GptModel {
    GPT3("gpt-3.5-turbo-0125");

    private final String name;

    GptModel(String name) {
        this.name = name;
    }
}
