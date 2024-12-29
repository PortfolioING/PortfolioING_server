package com.example.PING.service;

import com.example.PING.dto.request.ProjectRequestDto;
import com.example.PING.dto.response.PortfolioResponseDto;
import com.example.PING.dto.response.ProjectIdResponseDto;
import com.example.PING.dto.response.ProjectResponseDto;
import com.example.PING.entity.Portfolio;
import com.example.PING.entity.Project;
import com.example.PING.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Transactional
    public ProjectIdResponseDto createProject(ProjectRequestDto requestDto) {
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
        return new ProjectIdResponseDto(savedProject.getProjectId());
    }

    @Transactional(readOnly = true)
    public ProjectResponseDto getProjectById(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found with ID: " + projectId));
        return ProjectResponseDto.from(project);
    }
    @Transactional
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }

}
