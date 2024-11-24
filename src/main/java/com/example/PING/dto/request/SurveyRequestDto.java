package com.example.PING.dto.request;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class SurveyRequestDto {
    private String name;
    private String introduce;
    private String profile;
    private List<Long> projectsId;
}

