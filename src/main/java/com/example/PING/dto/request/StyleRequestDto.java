package com.example.PING.dto.request;

import jakarta.validation.constraints.NotNull;

public record StyleRequestDto(
        @NotNull(message = "Style ID is required.")
        Long style_id,

        String mainColor,
        String subColor,
        String backgroundColor
) {
}
