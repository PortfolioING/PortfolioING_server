package com.example.PING.componentstyle.dto.request;

import jakarta.validation.constraints.NotNull;

public record ComponentStyleRequest(
        Boolean bold,

        Boolean italic,

        Boolean underscore,

        Boolean strikethrough,

        @NotNull(message = "User ID is required.")
        Long size,

        String textColor,

        String backgroundColor

) {
}
