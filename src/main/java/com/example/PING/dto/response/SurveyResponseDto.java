package com.example.PING.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class SurveyResponseDto {
    private Long surveyId;
    private Long portfolioId;
    private String name;
    private String PR;
    private String pic;
    private List<ProjectResponseDto> projects;
    private String createdAt;
    private String updatedAt;
}
