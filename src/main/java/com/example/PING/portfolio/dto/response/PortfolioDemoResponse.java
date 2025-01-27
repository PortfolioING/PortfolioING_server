package com.example.PING.portfolio.dto.response;

import com.example.PING.portfolio.entity.Portfolio;
import com.example.PING.portfolio.entity.TechStack;

import java.time.LocalDateTime;
import java.util.List;

public record PortfolioDemoResponse(
        Long portfolioId,
        List<TechStack> techStack,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

){
    public static PortfolioDemoResponse from(Portfolio portfolio) {
        return new PortfolioDemoResponse(
                portfolio.getPortfolioId(),
                portfolio.getTechStack(),
                portfolio.getCreatedAt(),
                portfolio.getUpdatedAt()
                );
    }
}
