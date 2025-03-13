package com.example.PING.user.dto.response;

import com.example.PING.user.entity.User;

public record UserProfileResponse(
        String nickname,
        String userIcon
) {
    public static UserProfileResponse from(User user) {
        return new UserProfileResponse(
                user.getNickname(),
                user.getUserIcon()
        );
    }
}
