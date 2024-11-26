package com.example.PING.dto.response;

import com.example.PING.entity.Project;
import com.example.PING.entity.Survey;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SurveyResponseDto {
    private Long surveyId;
    private Long portfolioId;
    private String name;
    private String introduce;
    private String profile;
    private List<ProjectResponseDto> projects;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public SurveyResponseDto(Survey survey) {
        this.surveyId = survey.getSurveyId();
        this.portfolioId = survey.getPortfolio().getPortfolioId();
        this.name = survey.getName();
        this.introduce = survey.getIntroduce();
        this.profile = survey.getProfile();
        this.projects = new ArrayList<>();
        survey.getProjects().stream().map(project -> ProjectResponseDto.builder()
                .project(project).build())
                .forEach(dto -> this.projects.add(dto));
        this.createdAt = survey.getCreatedAt();
        this.updatedAt = survey.getUpdatedAt();
    }
}
