package com.example.PING.portfolio.dto.request;

import jakarta.validation.constraints.NotNull;

public record PortfolioCreateRequest(
        @NotNull(message = "User ID is required.")
        Long user_id,

        @NotNull(message = "Survey ID is required.")
        Long survey_id,

        @NotNull(message = "Style ID is required.")
        Long style_id,

        String title,
        String description,
        String image
) {
}