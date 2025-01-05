package com.example.PING.dto.request;

public record UserUpdateRequest(
        String name,
        String password,
        String nickname,
        String profilePic
) {
}
