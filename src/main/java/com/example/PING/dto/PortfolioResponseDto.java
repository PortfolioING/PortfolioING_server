package com.example.PING.dto;

import com.example.PING.entity.Portfolio;
import lombok.*;

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

    @Builder
    public PortfolioResponseDto(Portfolio portfolio) {
        this.portfolioId = portfolio.getPortfolioId();
        this.userId = portfolio.getUser().getUserId();
        this.surveyId = portfolio.getSurvey().getSurveyId();
        this.templateId = portfolio.getTemplate().getTemplateId();
        this.title = portfolio.getTitle();
        this.description = portfolio.getDescription();
        this.createdAt = portfolio.getCreatedAt();
        this.updatedAt = portfolio.getUpdatedAt();
    }
}
