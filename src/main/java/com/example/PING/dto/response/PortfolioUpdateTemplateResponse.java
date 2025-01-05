package com.example.PING.dto.response;

import java.time.LocalDateTime;

public record PortfolioUpdateTemplateResponse(
        Long portfolioId,
        Long templateId,
        LocalDateTime updatedAt
){
}

