package com.example.PING.project.service;

import com.example.PING.project.dto.request.ProjectRequest;
import com.example.PING.project.dto.response.ProjectIdResponse;
import com.example.PING.project.dto.response.ProjectResponse;
import com.example.PING.project.entity.Project;
import com.example.PING.error.exception.ResourceNotFoundException;
import com.example.PING.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Transactional
    public ProjectIdResponse createProject(ProjectRequest requestDto) {
        Project project = Project.builder()
                .projectName(requestDto.projectName())
                .image(requestDto.image())
                .projectDesc(requestDto.projectDesc())
                .projectFullDesc(requestDto.projectFullDesc())
                .projectLink(requestDto.projectLink())
                .startDate(requestDto.startDate())
                .endDate(requestDto.endDate())
                .roles(requestDto.roles())
                .pns(requestDto.pns())
                .build();

        // Project 엔티티 저장
        Project savedProject = projectRepository.save(project);

        // 응답 DTO 변환
        return new ProjectIdResponse(savedProject.getProjectId());
    }

    @Transactional(readOnly = true)
    public ProjectResponse getProjectById(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with ID: " + projectId));
        return ProjectResponse.from(project);
    }
    @Transactional
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }

}
