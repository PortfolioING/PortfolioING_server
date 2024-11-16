package com.example.PING.dto.response;


import java.util.List;

public record UserResponseDto(
        Long userId,
        String name,
        String email,
        String nickname,
        String profilePic
) {
}
