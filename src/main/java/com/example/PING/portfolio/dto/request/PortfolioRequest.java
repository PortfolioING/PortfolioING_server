package com.example.PING.portfolio.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

public record PortfolioRequest(
        @NotNull(message = "User ID is required.")
        Long user_id,

        @NotNull(message = "Survey ID is required.")
        Long survey_id,

        @NotNull(message = "Template ID is required.")
        Long template_id,

        String mainColor,
        String subColor,
        String backgroundColor,

        String title,
        String description
) {
}