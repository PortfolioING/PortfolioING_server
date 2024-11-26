package com.example.PING.dto.response;

import com.example.PING.entity.Portfolio;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter @Setter
@NoArgsConstructor
public class PortfolioResponseDto {
    private Long portfolioId;  // User ID
    private Long userId;
    private SurveyResponseDto surveyDto;
    private Long templateId;  // Template ID
    private String title;
    private String description;
    private String mainColor; // 컬러값 요소 포함
    private String subColor;
    private String backgroundColor;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public PortfolioResponseDto(Portfolio portfolio) {
        this.portfolioId = portfolio.getPortfolioId();
        this.userId = portfolio.getUser().getUserId();
        this.surveyDto = SurveyResponseDto.builder()
                .survey(portfolio.getSurvey())
                .build();
        this.templateId = portfolio.getTemplate().getTemplateId();
        this.title = portfolio.getTitle();
        this.mainColor = portfolio.getMainColor();
        this.subColor = portfolio.getSubColor();
        this.backgroundColor = portfolio.getBackgroundColor();
        this.description = portfolio.getDescription();
        this.createdAt = portfolio.getCreatedAt();
        this.updatedAt = portfolio.getUpdatedAt();
    }
}
