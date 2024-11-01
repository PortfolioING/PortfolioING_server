package com.example.PING.dto.request;

import lombok.Data;

@Data
public class DomainRequestDto {
    private String domain;
    private Long portfolioId;  // Portfolio ID
}
