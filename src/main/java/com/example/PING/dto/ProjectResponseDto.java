package com.example.PING.dto;

import com.example.PING.entity.Project;
import lombok.Builder;
import lombok.Data;

@Data
public class ProjectResponseDto {
    private Long projectId;
    private String projectName;

    @Builder
    public ProjectResponseDto(Project project) {
        this.projectId = project.getProjectId();
        this.projectName = project.getProjectName();
    }
}
