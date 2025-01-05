package com.example.PING.dto.response;

import com.example.PING.entity.Survey;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record SurveyResponse(
        Long surveyId,
        Long portfolioId,
        String name,
        String introduce,
        String profile,
        List<ProjectResponse> projects,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static SurveyResponse from(Survey survey) {
        return new SurveyResponse(
                survey.getSurveyId(),
                survey.getPortfolio().getPortfolioId(),
                survey.getName(),
                survey.getIntroduce(),
                survey.getProfile(),
                survey.getProjects().stream()
                        .map(ProjectResponse::from)
                        .collect(Collectors.toList()),
                survey.getCreatedAt(),
                survey.getUpdatedAt()
        );
    }
}