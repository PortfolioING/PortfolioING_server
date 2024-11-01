package com.example.PING.dto.request;

import lombok.Data;

@Data
public class ProjectRequestDto {
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
