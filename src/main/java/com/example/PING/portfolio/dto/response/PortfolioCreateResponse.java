package com.example.PING.portfolio.dto.response;

import java.time.LocalDateTime;

public record PortfolioCreateResponse(
        Long portfolioId,
        LocalDateTime createdAt
){
}
