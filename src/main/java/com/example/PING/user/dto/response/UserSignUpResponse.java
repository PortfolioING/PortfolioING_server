package com.example.PING.user.dto.response;

import com.example.PING.user.entity.User;

import java.time.LocalDateTime;

public record UserSignUpResponse(
        Long userId,
        String name,
        String email,
        String nickname,
        String userIcon,
        LocalDateTime created_at,
        LocalDateTime updated_at
) {
    public static UserSignUpResponse from(User user) {
        return new UserSignUpResponse(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getNickname(),
                user.getUserIcon(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
