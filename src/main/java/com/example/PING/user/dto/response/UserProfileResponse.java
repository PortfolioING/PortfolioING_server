package com.example.PING.user.dto.response;

import com.example.PING.user.entity.User;

public record UserProfileResponse(
        String name,
        String email,
        String nickname,
        String userIcon
) {
    public static UserProfileResponse from(User user) {
        return new UserProfileResponse(
                user.getName(),
                user.getEmail(),
                user.getNickname(),
                user.getUserIcon()
        );
    }
}
