package com.example.PING.dto.request;

import jakarta.validation.constraints.NotNull;

public record PortfolioRequestDto(
        @NotNull(message = "User ID is required.")
        Long user_id,

        @NotNull(message = "Survey ID is required.")
        Long survey_id,

        @NotNull(message = "Template ID is required.")
        Long template_id,

        String title,
        String description,
        String mainColor,
        String subColor,
        String backgroundColor
) {
}