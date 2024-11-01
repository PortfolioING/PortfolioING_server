package com.example.PING.dto.request;

public record UserRequestDto(
        String name,
        String email,
        String password,
        String nickname,
        String profilePic
) {
}
