package com.example.PING.dto;

import com.example.PING.entity.Survey;
import lombok.Builder;
import lombok.Data;

@Data
public class SurveyResponseDto {
    private Long surveyId;
    private String name;
    private String PR;
    private String pic;

    @Builder
    public SurveyResponseDto(Survey survey) {
        this.surveyId = surveyId;
        this.name = name;
        this.PR = PR;
        this.pic = pic;
    }
}
