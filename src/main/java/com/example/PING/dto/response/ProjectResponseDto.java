package com.example.PING.dto.response;

import lombok.Data;

@Data
public class ProjectResponseDto {
    private Long projectId;
    private String projectName;
    private String image;
    private String shortIntro;
    private String longIntro;
    private String date;
    private String target;
    private String role;
    private String problem;
    private String solution;
    private String feedback;
}
