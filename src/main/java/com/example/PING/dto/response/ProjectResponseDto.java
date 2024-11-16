package com.example.PING.dto.response;

import com.example.PING.entity.Project;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Setter
public class ProjectResponseDto {
    private Long projectId;
    private String projectName;
    private LocalDateTime createdAt;
}