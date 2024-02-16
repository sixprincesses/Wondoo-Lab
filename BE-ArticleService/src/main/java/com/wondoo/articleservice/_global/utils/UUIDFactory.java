package com.wondoo.articleservice._global.utils;

import org.springframework.stereotype.Component;
import java.util.UUID;

@Component
public class UUIDFactory {
    public String generateUUIDWithParam(String param) {
        return UUID.nameUUIDFromBytes(param.getBytes()).toString();
    }

    public String generateUUIDWithoutParam() {
        return UUID.randomUUID().toString();
    }
}
