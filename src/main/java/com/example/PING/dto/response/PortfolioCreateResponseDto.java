package com.example.PING.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public class PortfolioCreateResponseDto {
    private Long portfolioId;
    private LocalDateTime createdAt;
}
