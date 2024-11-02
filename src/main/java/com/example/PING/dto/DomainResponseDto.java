package com.example.PING.dto;

import com.example.PING.entity.Domain;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DomainResponseDto {
    private Long domain_id;
    private Long portfolio_id;
    private String domain;
    private LocalDateTime createdAt;

    @Builder
    public DomainResponseDto(Domain domain) {
        this.domain_id = domain.getDomainId();
        this.portfolio_id = domain.getPortfolio().getPortfolioId();
        this.domain = domain.getDomain();
        this.createdAt = domain.getCreatedAt();
    }
}
