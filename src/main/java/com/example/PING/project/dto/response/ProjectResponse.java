package com.example.PING.project.dto.response;

import com.example.PING.project.entity.ProblemSolution;
import com.example.PING.project.entity.Project;
import com.example.PING.project.entity.Role;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public record ProjectResponse(
        Long projectId,
        Long surveyId,
        String projectName,
        String image,
        String projectDesc,
        String projectFullDesc,
        String projectLink,
        LocalDate startDate,
        LocalDate endDate,
        Set<Role> roles,
        List<ProblemSolution> pns
) {
    public static ProjectResponse from(Project project) {
        return new ProjectResponse(
                project.getProjectId(),
                project.getSurvey().getSurveyId(),
                project.getProjectName(),
                project.getImage(),
                project.getProjectDesc(),
                project.getProjectFullDesc(),
                project.getProjectLink(),
                project.getStartDate(),
                project.getEndDate(),
                project.getRoles(),
                project.getPns()
        );
    }
}
