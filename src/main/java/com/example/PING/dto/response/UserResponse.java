package com.example.PING.dto.response;

import com.example.PING.entity.User;

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
