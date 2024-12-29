package com.example.PING.dto.request;

import com.example.PING.entity.ProblemSolution;

import java.time.LocalDate;
import java.util.List;

public record ProjectRequestDto (
        String projectName,
        String image,
        String projectDesc,
        String projectFullDesc,
        String projectLink,
        LocalDate startDate,
        LocalDate endDate,
        List<String> roles,
        List<ProblemSolution> pns
){
}
