package com.example.PING.auth.dto.response;

public record SocialLoginResponse(Long userId,
                                  String nickname,
                                  String email) {
    public static SocialLoginResponse of(LoginResponse loginResponse) {
        return new SocialLoginResponse(loginResponse.userId(), loginResponse.nickname(), loginResponse.email());
    }
}
