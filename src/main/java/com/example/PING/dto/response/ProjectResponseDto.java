package com.example.PING.dto.response;

import com.example.PING.entity.Project;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjectResponseDto {
    private Long projectId;
    private String projectName;
    private String image;
    private String shortIntro;
    private String longIntro;
    private String date;
    private String target;
    private String role;
    private String problem;
    private String solution;
    private String feedback;

    @Builder
    public ProjectResponseDto(Project project) {
        this.projectId = project.getProjectId();
        this.projectName = project.getProjectName();
        this.image = project.getImage();
        this.shortIntro = project.getShortIntro();
        this.longIntro = project.getLongIntro();
        this.date = String.valueOf(project.getDate());
        this.target = project.getTarget();
        this.role = project.getRole();
        this.solution = project.getSolution();
        this.feedback = project.getFeedback();
    }
}
