package com.example.PING.dto.response;

import com.example.PING.entity.Survey;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

public record SurveyCreateResponseDto (
        Long surveyId,
        LocalDateTime createdAt
){
    public static SurveyCreateResponseDto from(Survey survey) {
        return new SurveyCreateResponseDto(
                survey.getSurveyId(),
                survey.getCreatedAt()
        );
    }
}
