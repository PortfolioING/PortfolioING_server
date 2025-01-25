package com.example.PING.user.dto.request;

public record UserUpdateRequest(
        String name,
        String password,
        String nickname,
        String userIcon
) {
}
