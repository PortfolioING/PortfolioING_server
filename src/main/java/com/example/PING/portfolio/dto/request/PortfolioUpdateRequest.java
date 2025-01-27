package com.example.PING.portfolio.dto.request;

import jakarta.validation.constraints.NotNull;

public record PortfolioUpdateRequest(
        String titleImg
) {
}