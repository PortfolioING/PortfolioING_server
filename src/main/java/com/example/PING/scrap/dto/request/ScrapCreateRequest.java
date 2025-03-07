package com.example.PING.scrap.dto.request;

import jakarta.validation.constraints.NotNull;

public record ScrapCreateRequest(
        @NotNull(message = "Portfolio ID is required.")
        Long portfolioId,

        @NotNull(message = "User ID is required.")
        Long userId
) {
}
