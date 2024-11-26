package com.example.PING.dto.response;

public record UserUpdateResponseDto(
        String name,
        String nickname,
        String password,
        String profilePic
) {
}
