package com.example.PING.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record UserUpdateResponseDto(
        Long userId,
        String name,
        String email,
        String nickname,
        String profilePic,
        LocalDateTime updated_at
) {
}
