package com.example.PING.user.dto.request;

public record UserProfileUpdateRequest(
        String name,
        String email,
        String password,
        String nickname,
        String userIcon
) {
}
