package com.example.PING.dto.request;

import java.util.List;

public record SurveyRequestDto (
        String name,
        String introduce,
        String profile,
        List<Long> projectsId
){
}

