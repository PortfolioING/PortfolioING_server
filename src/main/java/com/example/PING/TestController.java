package com.example.PING;

import com.example.PING.entity.Project;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class TestController {
    private final TestProjectRepository testProjectRepository;

    @GetMapping("/projects")
    public List<Project> getAllProjects() {
        return testProjectRepository.findAll();
    }
}