package com.example.PING.user.dto.response;

public record UserUpdateResponse(
        String name,
        String nickname,
        String password,
        String userIcon
) {
}
