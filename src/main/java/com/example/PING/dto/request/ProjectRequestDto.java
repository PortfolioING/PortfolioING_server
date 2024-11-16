package com.example.PING.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
public class ProjectRequestDto {
    private Long projectId;
    private String project_name;
    private String image;
    private String short_intro;
    private String long_intro;
    private LocalDateTime date;
    private String target;
    private String role;
    private String problem;
    private String solution;
    private String feedback;
}
