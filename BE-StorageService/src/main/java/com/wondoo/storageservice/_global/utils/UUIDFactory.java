package com.wondoo.storageservice._global.utils;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDFactory {
    public String generateUUID(){
        return UUID.randomUUID().toString();
    }
}
