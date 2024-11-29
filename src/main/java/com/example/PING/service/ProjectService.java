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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    @Transactional
    public ProjectIdResponseDto createProject(ProjectRequestDto requestDto) {

//        System.out.println(requestDto);

        Project project = Project.builder()
                .projectName(requestDto.getProjectName())
                .image(requestDto.getImage())
                .projectDesc(requestDto.getProjectDesc())
                .projectFullDesc(requestDto.getProjectFullDesc())
                .projectLink(requestDto.getProjectLink())
                .startDate(requestDto.getStartDate())
                .endDate(requestDto.getEndDate())
                .roles(requestDto.getRoles())
                .pns(requestDto.getPns())
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
        return ProjectResponseDto.builder()
                .project(project)
                .build();
    }
    @Transactional
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }

}
