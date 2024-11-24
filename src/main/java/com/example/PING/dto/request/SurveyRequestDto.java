package com.example.PING.dto.request;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class SurveyRequestDto {
    private String title;
    private String introduce;
    private String image;
    private List<Long> projectsId;
}

