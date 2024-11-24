package com.example.PING.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class SurveyCreateResponseDto {
    private Long surveyId;
    private LocalDateTime createdAt;
}
