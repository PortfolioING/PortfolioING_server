package com.example.PING.dto.response;

public record LoginResponseDto(
        Long userId,
        String name,
        String email,
        String nickname,
        String profilePic
//        String token
) {
}
