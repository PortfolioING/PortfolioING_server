package com.example.PING.survey.dto.request;

import java.util.List;

public record SurveyRequest(
        String name,
        String introduce,
        String profile,
        List<Long> projectsId
){
}

