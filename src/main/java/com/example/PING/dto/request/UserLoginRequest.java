package com.example.PING.dto.request;

public record UserLoginRequest(
        String email,
        String password
) {
}