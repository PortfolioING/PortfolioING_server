package com.example.PING.dto.response;

import com.example.PING.entity.ProblemSolution;
import com.example.PING.entity.Project;

import java.time.LocalDate;
import java.util.List;

public record ProjectResponseDto(
        Long projectId,
        Long surveyId,
        String projectName,
        String image,
        String projectDesc,
        String projectFullDesc,
        String projectLink,
        LocalDate startDate,
        LocalDate endDate,
        List<String> roles,
        List<ProblemSolution> pns
) {
    public static ProjectResponseDto from(Project project) {
        return new ProjectResponseDto(
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
