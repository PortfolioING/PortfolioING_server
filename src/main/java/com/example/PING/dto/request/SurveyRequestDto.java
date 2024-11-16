package com.example.PING.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class SurveyRequestDto {
    private String name;
    private String pr;
    private String pic;
    private List<ProjectRequestDto> projects;
}

