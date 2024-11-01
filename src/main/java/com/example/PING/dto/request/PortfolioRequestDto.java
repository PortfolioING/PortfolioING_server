package com.example.PING.dto.request;

import lombok.Data;

@Data
public class PortfolioRequestDto {
    private String title;
    private String description;
    private Long userId;  // User ID
    private Long templateId;  // Template ID
}
