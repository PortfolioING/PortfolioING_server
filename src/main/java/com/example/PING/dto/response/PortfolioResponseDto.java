package com.example.PING.dto.response;

import com.example.PING.entity.Portfolio;
import java.time.LocalDateTime;

public record PortfolioResponseDto(
        Long portfolioId,
        Long userId,
        SurveyResponseDto surveyDto,
        Long templateId,
        String title,
        String description,
        StyleResponseDto styleDto,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PortfolioResponseDto from(Portfolio portfolio) {
        return new PortfolioResponseDto(
                portfolio.getPortfolioId(),
                portfolio.getUser().getUserId(),
                SurveyResponseDto.from(portfolio.getSurvey()),
                portfolio.getTemplate().getTemplateId(),
                portfolio.getTitle(),
                portfolio.getDescription(),
                StyleResponseDto.from(portfolio.getStyle()),
                portfolio.getCreatedAt(),
                portfolio.getUpdatedAt()
        );
    }
}