package com.example.PING.service;

import com.example.PING.dto.ProjectRequestDto;
import com.example.PING.dto.ProjectResponseDto;
import com.example.PING.entity.Project;
import com.example.PING.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Transactional
    public ProjectResponseDto createProject(ProjectRequestDto projectRequestDto) {
        Project project = Project.builder()
                .projectName(projectRequestDto.getProjectName())
                .build();
        // Portfolio 설정 추가 필요
        return convertToResponseDto(projectRepository.save(project));
    }

    @Transactional(readOnly = true)
    public List<ProjectResponseDto> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ProjectResponseDto getProjectById(Long projectId) {
        return convertToResponseDto(projectRepository.findById(projectId).orElse(null));
    }

    @Transactional
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }

    private ProjectResponseDto convertToResponseDto(Project project) {
        return ProjectResponseDto.builder()
                .project(project)
                .build();
    }
}
