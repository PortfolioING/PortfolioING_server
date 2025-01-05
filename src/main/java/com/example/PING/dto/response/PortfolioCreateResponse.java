package com.example.PING.dto.response;

import java.time.LocalDateTime;

public record PortfolioCreateResponse(
        Long portfolioId,
        LocalDateTime createdAt
){
}
