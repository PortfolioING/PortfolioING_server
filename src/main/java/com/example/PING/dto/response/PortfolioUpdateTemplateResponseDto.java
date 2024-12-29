package com.example.PING.dto.response;

import java.time.LocalDateTime;

public record PortfolioUpdateTemplateResponseDto (
        Long portfolioId,
        Long templateId,
        LocalDateTime updatedAt
){
}

