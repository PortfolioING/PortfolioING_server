package com.example.PING.dto.response;

import java.time.LocalDateTime;

public record SignUpResponseDto(
        Long userId,
        String name,
        String email,
        String nickname,
        String profilePic,
        LocalDateTime created_at,
        LocalDateTime updated_at
) {
}
