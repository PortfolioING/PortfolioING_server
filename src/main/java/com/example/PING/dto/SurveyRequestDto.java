package com.example.PING.dto;

import lombok.Data;

@Data
public class SurveyRequestDto {
    private String name;
    private String PR;
    private String pic;
    private Long portfolioId;  // Portfolio ID
}
