package com.example.PING.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter @Setter
@NoArgsConstructor
public class PortfolioResponseDto {
    private Long portfolioId;  // User ID
    private Long userId;
    private Long surveyId;
    private Long templateId;  // Template ID
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
