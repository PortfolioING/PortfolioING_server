package com.example.PING.portfolio.dto.request;

import jakarta.validation.constraints.NotNull;

public record PortfolioCreateRequest(
        @NotNull(message = "User ID is required.")
        Long user_id,
        @NotNull(message = "Component ID is required.")
        Long component_id,
        @NotNull(message = "Template ID is required.")
        Long template_id,
        String title_img
) {
}