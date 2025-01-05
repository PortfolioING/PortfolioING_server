package com.example.PING.dto.response;

import com.example.PING.entity.Portfolio;
import java.time.LocalDateTime;

public record PortfolioResponse(
        Long portfolioId,
        Long userId,
        SurveyResponse surveyDto,
        Long templateId,
        String title,
        String description,
        StyleResponse styleDto,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PortfolioResponse from(Portfolio portfolio) {
        return new PortfolioResponse(
                portfolio.getPortfolioId(),
                portfolio.getUser().getUserId(),
                SurveyResponse.from(portfolio.getSurvey()),
                portfolio.getTemplate().getTemplateId(),
                portfolio.getTitle(),
                portfolio.getDescription(),
                StyleResponse.from(portfolio.getStyle()),
                portfolio.getCreatedAt(),
                portfolio.getUpdatedAt()
        );
    }
}