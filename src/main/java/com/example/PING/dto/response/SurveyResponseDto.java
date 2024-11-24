package com.example.PING.dto.response;

import com.example.PING.entity.Survey;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class SurveyResponseDto {
    private Long surveyId;
    private Long portfolioId;
    private String name;
    private String introduce;
    private String profile;
    private List<ProjectIdResponseDto> projects;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
