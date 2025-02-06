package com.example.PING.auth.dto.response;

import com.example.PING.user.entity.User;

public record LoginResponse(
        Long userId,
        String nickname,
        String email,
        String accessToken, // 헤더에 박아서 줄 거라 필요X 인 건지 고민을 좀...
        String refreshToken
) {
    public static LoginResponse of(User user, TokenPairResponse tokenPairResponse) {
        return new LoginResponse(user.getUserId(), user.getNickname(), user.getOauthInfo().getOauthEmail(), tokenPairResponse.accessToken(), tokenPairResponse.refreshToken());
    }
}
