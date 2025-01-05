package com.example.PING.dto.request;

import java.util.List;

public record SurveyRequest(
        String name,
        String introduce,
        String profile,
        List<Long> projectsId
){
}

