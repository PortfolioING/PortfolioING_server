package com.example.PING.dto.response;

import com.example.PING.entity.Domain;

import java.time.LocalDateTime;

public record DomainResponseDto(
        Long domainId,
        Long portfolioId,
        String domain,
        LocalDateTime createdAt
) {
    public static DomainResponseDto from(Domain domain) {
        return new DomainResponseDto(
                domain.getDomainId(),
                domain.getPortfolio() != null ? domain.getPortfolio().getId() : null,
                domain.getDomain(),
                domain.getCreatedAt()
        );
    }
}