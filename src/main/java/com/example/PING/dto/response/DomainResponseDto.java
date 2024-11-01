package com.example.PING.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DomainResponseDto {
    private Long domain_id;
    private Long portfolio_id;
    private String domain;
    private LocalDateTime createdAt;
}
