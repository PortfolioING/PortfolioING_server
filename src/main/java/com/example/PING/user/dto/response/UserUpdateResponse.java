package com.example.PING.user.dto.response;

import com.example.PING.user.entity.User;

import java.time.LocalDateTime;

public record UserUpdateResponse(
        Long userId,
        String nickname,
        String userIcon,
        LocalDateTime updated_at
) {
    public static UserUpdateResponse from(User user) {
        return new UserUpdateResponse(
                user.getUserId(),
                user.getNickname(),
                user.getUserIcon(),
                user.getUpdatedAt()
        );
    }
}
