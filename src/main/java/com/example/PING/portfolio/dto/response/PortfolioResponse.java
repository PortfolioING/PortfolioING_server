package com.example.PING.portfolio.dto.response;
import com.example.PING.portfolio.entity.Portfolio;

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