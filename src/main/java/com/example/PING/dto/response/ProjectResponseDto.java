package com.example.PING.dto.response;

import com.example.PING.entity.ProblemSolution;
import com.example.PING.entity.Project;
import com.example.PING.entity.Survey;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
public class ProjectResponseDto {
    private Long projectId;
    private Long surveyId;
    private String projectName;
    private String image;
    private String projectDesc;
    private String projectFullDesc;
    private String projectLink;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> roles;
    private List<ProblemSolution> pns;

    @Builder
    public ProjectResponseDto(Project project) {
        this.projectId = project.getProjectId();
        this.surveyId = project.getSurvey().getSurveyId();
        this.projectName = getProjectName();
        this.image = getImage();
        this.projectDesc = getProjectDesc();
        this.projectFullDesc = getProjectFullDesc();
        this.projectLink = getProjectLink();
        this.startDate = getStartDate();
        this.endDate = getEndDate();
        this.roles = getRoles();
        this.pns = getPns();
    }
}
