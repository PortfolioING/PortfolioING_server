package com.example.PING.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SurveyCreateResponseDto {
    private Long surveyId;
    private LocalDateTime createdAt;
}
