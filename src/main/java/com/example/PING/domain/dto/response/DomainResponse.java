package com.example.PING.domain.dto.response;

import com.example.PING.domain.entity.Domain;

import java.time.LocalDateTime;

public record DomainResponse(
        Long domainId,
        Long portfolioId,
        String domain,
        LocalDateTime createdAt
) {
    public static DomainResponse from(Domain domain) {
        return new DomainResponse(
                domain.getDomainId(),
                domain.getPortfolio() != null ? domain.getPortfolio().getId() : null,
                domain.getDomain(),
                domain.getCreatedAt()
        );
    }
}