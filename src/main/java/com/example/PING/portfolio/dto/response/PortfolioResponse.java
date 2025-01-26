package com.example.PING.portfolio.dto.response;

import com.example.PING.survey.dto.response.SurveyResponse;
import com.example.PING.portfolio.entity.Portfolio;
import java.time.LocalDateTime;

public record PortfolioResponse(
        Long portfolioId,
        Long userId,
        Long templateId,
        StyleResponse styleDto,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static PortfolioResponse from(Portfolio portfolio) {
        return new PortfolioResponse(
                portfolio.getPortfolioId(),
                portfolio.getUser().getUserId(),
                portfolio.getTemplate().getTemplateId(),
                StyleResponse.from(portfolio.getStyle()),
                portfolio.getCreatedAt(),
                portfolio.getUpdatedAt()
        );
    }
}