package com.example.PING.dto.response;

import com.example.PING.entity.Survey;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SurveyResponseDto {
    private Long surveyId;
    private Long portfolioId;
    private String name;
    private String PR;
    private String pic;
    private List<ProjectResponseDto> projects;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public SurveyResponseDto(Survey survey) {
        this.surveyId = surveyId;
        this.portfolioId = portfolioId;
        this.name = name;
        this.PR = PR;
        this.pic = pic;
        this.projects = projects;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
