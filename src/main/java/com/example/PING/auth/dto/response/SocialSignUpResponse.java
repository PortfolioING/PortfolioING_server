package com.example.PING.auth.dto.response;

public record SocialSignUpResponse(Long userId,
                                   String nickname) {
    public static SocialSignUpResponse of(LoginResponse loginResponse) {
        return new SocialSignUpResponse(loginResponse.userId(), loginResponse.nickname());
    }
}
