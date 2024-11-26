package com.example.PING.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class PortfolioCreateResponseDto {
    private Long portfolioId;
    private LocalDateTime createdAt;
}
