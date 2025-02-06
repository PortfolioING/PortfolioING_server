package com.example.PING.auth.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OAuthProvider {
    KAKAO("KAKAO"),
    APPLE("APPLE"),
    ;
    private final String value;

    public static OAuthProvider from(String provider) {
        return switch (provider.toUpperCase()) {
            case "APPLE" -> APPLE;
            case "KAKAO" -> KAKAO;
            default -> throw new IllegalArgumentException("Unknown provider: " + provider); // Todo 에러코드 이넘화 해놓기
        };
    }
}
