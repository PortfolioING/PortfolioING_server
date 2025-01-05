package com.example.PING.dto.request;

public record UserRequest(
        String name,
        String email,
        String password,
        String nickname,
        String profilePic
) {
}
