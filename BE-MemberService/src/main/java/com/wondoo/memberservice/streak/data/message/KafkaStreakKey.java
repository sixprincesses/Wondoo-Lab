package com.wondoo.memberservice.streak.data.message;

import lombok.Getter;

@Getter
public enum KafkaStreakKey {
    SAVE("SAVE"), DELETE("DELETE");

    private final String value;

    KafkaStreakKey(String value) {
        this.value = value;
    }
}
