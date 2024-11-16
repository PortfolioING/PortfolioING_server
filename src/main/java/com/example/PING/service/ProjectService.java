package com.example.PING.service;

import com.example.PING.dto.request.ProjectRequestDto;
import com.example.PING.dto.response.ProjectResponseDto;
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
    public ProjectResponseDto createProject(ProjectRequestDto projectRequestDto) {

        System.out.println(projectRequestDto);

        Project project = Project.builder()
                .projectName(projectRequestDto.getProject_name())
                .image(projectRequestDto.getImage())
                .shortIntro(projectRequestDto.getShort_intro())
                .longIntro(projectRequestDto.getLong_intro())
                .date(projectRequestDto.getDate())
                .target(projectRequestDto.getTarget())
                .role(projectRequestDto.getRole())
                .problem(projectRequestDto.getProblem())
                .solution(projectRequestDto.getSolution())
                .feedback(projectRequestDto.getFeedback())
                .build();

        // Project 엔티티 저장
        Project savedProject = projectRepository.save(project);

        // 응답 DTO 변환
        ProjectResponseDto responseDto = new ProjectResponseDto();
        responseDto.setProjectId(savedProject.getProjectId());
        responseDto.setProjectName(savedProject.getProjectName());
        responseDto.setCreatedAt(LocalDateTime.now());  // or set created_at if it exists

        return responseDto;
    }

    @Transactional
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }

}
