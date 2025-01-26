package com.example.PING.user.dto.response;

import com.example.PING.user.entity.User;

import java.time.LocalDateTime;

public record UserUpdateResponse(
        Long userId,
        String name,
        String nickname,
        String password,
        String userIcon,
        LocalDateTime updated_at
) {
    public static UserUpdateResponse from(User user) {
        return new UserUpdateResponse(
                user.getUserId(),
                user.getName(),
                user.getNickname(),
                user.getPassword(),
                user.getUserIcon(),
                user.getUpdatedAt()
        );
    }
}
