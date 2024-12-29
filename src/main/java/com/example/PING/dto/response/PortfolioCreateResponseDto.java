package com.example.PING.dto.response;

import java.time.LocalDateTime;

public record PortfolioCreateResponseDto (
        Long portfolioId,
        LocalDateTime createdAt
){
}
