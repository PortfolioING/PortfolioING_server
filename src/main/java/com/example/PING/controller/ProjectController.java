package com.example.PING.controller;

import com.example.PING.dto.request.ProjectRequestDto;
import com.example.PING.dto.response.PortfolioResponseDto;
import com.example.PING.dto.response.ProjectIdResponseDto;
import com.example.PING.dto.response.ProjectResponseDto;
import com.example.PING.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectIdResponseDto> createProject(@RequestBody ProjectRequestDto projectRequestDto) {
        ProjectIdResponseDto response = projectService.createProject(projectRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{project_id}")
    public ResponseEntity<ProjectResponseDto> getProjectById(@PathVariable("project_id") Long projectId) {
        ProjectResponseDto response = projectService.getProjectById(projectId);
        return ResponseEntity.ok(response);
    }

}
