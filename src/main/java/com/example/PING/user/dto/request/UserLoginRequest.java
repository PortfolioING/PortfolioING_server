package com.example.PING.user.dto.request;

public record UserLoginRequest(
        String email,
        String password
) {
}