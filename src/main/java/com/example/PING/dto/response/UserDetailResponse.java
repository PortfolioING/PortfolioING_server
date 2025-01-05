package com.example.PING.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record UserDetailResponse(
        Long userId,
        String name,
        String email,
        String nickname,
        String profilePic,
        List<Long> portfolio_ids,
        LocalDateTime created_at,
        LocalDateTime updated_at
) {
}
