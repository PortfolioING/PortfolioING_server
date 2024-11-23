package com.example.PING.dto.request;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class SurveyRequestDto {
    private String name;
    private String pr;
    private String pic;
    private List<Long> projectsId;
}

