package com.example.PING.user.dto.request;

public record UserProfileUpdateRequest(
        String nickname,
        String userIcon
) {
}
