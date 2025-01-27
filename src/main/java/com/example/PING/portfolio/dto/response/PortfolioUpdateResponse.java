package com.example.PING.portfolio.dto.response;

import com.example.PING.portfolio.entity.Portfolio;

public record PortfolioUpdateResponse(
        Long portfolioId,
        Long userId,
        String titleImg
) {
    public static PortfolioUpdateResponse from(Portfolio portfolio) {
        return new PortfolioUpdateResponse(
                portfolio.getPortfolioId(),
                portfolio.getUser().getUserId(),
                portfolio.getTitleImg()
        );
    }
}
