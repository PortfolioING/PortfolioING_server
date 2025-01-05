package com.example.PING.controller;

import com.example.PING.dto.request.ProjectRequest;
import com.example.PING.dto.response.ProjectIdResponse;
import com.example.PING.dto.response.ProjectResponse;
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
    public ResponseEntity<ProjectIdResponse> createProject(@RequestBody ProjectRequest projectRequest) {
        ProjectIdResponse response = projectService.createProject(projectRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{project_id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable("project_id") Long projectId) {
        ProjectResponse response = projectService.getProjectById(projectId);
        return ResponseEntity.ok(response);
    }

}
