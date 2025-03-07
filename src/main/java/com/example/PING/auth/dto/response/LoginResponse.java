package com.example.PING.auth.dto.response;

import com.example.PING.user.entity.User;

public record LoginResponse(
        Long userId,
        String nickname,
        String email,
        String accessToken,
        String refreshToken,
        String temporaryToken
) {
    public static LoginResponse of(User user, TokenSetResponse tokenSetResponse) {
        return new LoginResponse(user.getUserId(), user.getNickname(), user.getOauthInfo().getOauthEmail(), tokenSetResponse.accessToken(), tokenSetResponse.refreshToken(), tokenSetResponse.temporaryToken());
    }
}
