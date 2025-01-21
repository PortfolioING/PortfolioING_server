package com.example.PING.user.dto.response;

import java.time.LocalDateTime;

public record UserSignUpResponse(
        Long userId,
        String name,
        String email,
        String nickname,
        String profilePic,
        LocalDateTime created_at,
        LocalDateTime updated_at
) {
}
