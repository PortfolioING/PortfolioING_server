package com.example.PING.dto.response;

import com.example.PING.entity.Survey;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record SurveyResponseDto(
        Long surveyId,
        Long portfolioId,
        String name,
        String introduce,
        String profile,
        List<ProjectResponseDto> projects,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static SurveyResponseDto from(Survey survey) {
        return new SurveyResponseDto(
                survey.getSurveyId(),
                survey.getPortfolio().getPortfolioId(),
                survey.getName(),
                survey.getIntroduce(),
                survey.getProfile(),
                survey.getProjects().stream()
                        .map(ProjectResponseDto::from)
                        .collect(Collectors.toList()),
                survey.getCreatedAt(),
                survey.getUpdatedAt()
        );
    }
}