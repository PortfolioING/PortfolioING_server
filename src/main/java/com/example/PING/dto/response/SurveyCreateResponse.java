package com.example.PING.dto.response;

import com.example.PING.entity.Survey;

import java.time.LocalDateTime;

public record SurveyCreateResponse(
        Long surveyId,
        LocalDateTime createdAt
){
    public static SurveyCreateResponse from(Survey survey) {
        return new SurveyCreateResponse(
                survey.getSurveyId(),
                survey.getCreatedAt()
        );
    }
}
