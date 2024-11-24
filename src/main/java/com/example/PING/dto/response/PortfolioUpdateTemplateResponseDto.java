package com.example.PING.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PortfolioUpdateTemplateResponseDto {
    private Long portfolioId;
    private Long templateId;
    private LocalDateTime updatedAt;
}

