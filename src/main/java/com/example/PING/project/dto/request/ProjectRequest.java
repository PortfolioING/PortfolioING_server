package com.example.PING.project.dto.request;

import com.example.PING.project.entity.ProblemSolution;
import com.example.PING.project.entity.Role;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public record ProjectRequest(
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
