package com.example.PING.dto.request;

import com.example.PING.entity.ProblemSolution;
import com.example.PING.entity.Role;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public record ProjectRequestDto (
        String projectName,
        String image,
        String projectDesc,
        String projectFullDesc,
        String projectLink,
        LocalDate startDate,
        LocalDate endDate,
        Set<Role> roles,
        List<ProblemSolution> pns
){
}
