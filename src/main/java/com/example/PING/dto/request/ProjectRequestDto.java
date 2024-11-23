package com.example.PING.dto.request;

import com.example.PING.entity.ProblemSolution;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Data
@Getter
public class ProjectRequestDto {
    private String projectName;
    private String image;
    private String projectDesc;
    private String projectFullDesc;
    private String projectLink;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> roles;
    private List<ProblemSolution> pns;
}
