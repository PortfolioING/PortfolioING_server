package com.example.PING.dto.response;

public record UserUpdateResponse(
        String name,
        String nickname,
        String password,
        String profilePic
) {
}
