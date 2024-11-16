package com.example.PING.controller;

import com.example.PING.dto.request.ProjectRequestDto;
import com.example.PING.dto.response.ProjectResponseDto;
import com.example.PING.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponseDto> createProject(@RequestBody ProjectRequestDto projectRequestDto) {
        ProjectResponseDto response = projectService.createProject(projectRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
