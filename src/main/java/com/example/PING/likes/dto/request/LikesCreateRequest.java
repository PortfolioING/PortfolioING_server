package com.example.PING.likes.dto.request;

import jakarta.validation.constraints.NotNull;

public record LikesCreateRequest(
        @NotNull(message = "Portfolio ID is required.")
        Long portfolioId,

        @NotNull(message = "User ID is required.")
        Long userId
) {
}
