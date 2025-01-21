package com.example.PING.user.dto.response;

import com.example.PING.user.entity.User;

public record UserResponse(
        Long userId,
        String name,
        String email,
        String nickname,
        String profilePic
) {
    public static UserResponse from(User user) {
        return new UserResponse(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getNickname(),
                user.getProfilePic()
        );
    }
}
