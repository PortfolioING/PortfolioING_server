package com.example.PING.dto.request;

public record UserUpdateRequestDto(
        String name,
        String password,
        String nickname,
        String profilePic
) {
}
