package com.example.PING.user.dto.request;

public record UserSignUpRequest(
        String name,
        String email,
        String password,
        String nickname,
        String profilePic
) {
}
