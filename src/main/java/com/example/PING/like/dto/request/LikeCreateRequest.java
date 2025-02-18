package com.example.PING.like.dto.request;

import com.example.PING.portfolio.entity.Portfolio;
import com.example.PING.user.entity.User;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record LikeCreateRequest(
        @NotNull(message = "Portfolio ID is required.")
        Long portfolioId,

        @NotNull(message = "User ID is required.")
        Long userId
) {
}
