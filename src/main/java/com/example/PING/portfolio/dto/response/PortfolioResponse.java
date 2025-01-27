package com.example.PING.portfolio.dto.response;

import com.example.PING.survey.dto.response.SurveyResponse;
import com.example.PING.portfolio.entity.Portfolio;
import java.time.LocalDateTime;

public record PortfolioResponse(
        Long portfolioId,
        Long templateId,
        Long componentId
) {
    public static PortfolioResponse from(Portfolio portfolio) {
        return new PortfolioResponse(
                portfolio.getPortfolioId(),
                portfolio.getTemplate().getTemplateId(),
                portfolio.getComponent().getComponentId()
        );
    }
}