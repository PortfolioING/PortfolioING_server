package com.example.PING.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

public record PortfolioRequestDto(
        @NotNull(message = "User ID is required.")
        Long user_id,

        @NotNull(message = "Survey ID is required.")
        Long survey_id,

        @NotNull(message = "Template ID is required.")
        Long template_id,

        Long style_id, // style_id는 선택적

        String mainColor,
        String subColor,
        String backgroundColor,

        String title,
        String description
) {
}