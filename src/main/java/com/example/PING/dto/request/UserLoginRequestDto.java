package com.example.PING.dto.request;

import lombok.Data;

public record UserLoginRequestDto(
        String email,
        String password
) {
}