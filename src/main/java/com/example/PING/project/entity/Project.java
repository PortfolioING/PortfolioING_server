package com.example.PING.project.entity;
import com.example.PING.survey.entity.Survey;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    @Setter
    @ManyToOne
    @JoinColumn(name = "survey_id", nullable = true)
    private Survey survey;

    @Column(name = "project_name", length = 128)
    private String projectName;

    @Column(name = "image", length = 128)
    private String image;

    @Column(name = "project_desc", length = 128)
    private String projectDesc;

    @Column(name = "project_full_desc", length = 128)
    private String projectFullDesc;

    @Column(name = "project_link", length = 128)
    private String projectLink;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ElementCollection(targetClass = Role.class)
    @Enumerated(EnumType.STRING)
    @Column(name = "roles")
    private Set<Role> roles = new HashSet<>();

    @ElementCollection
    private List<ProblemSolution> pns = new ArrayList<>(); // 문제와 해결책 리스트

    @Builder
    public Project(String projectName, String image, String projectDesc, String projectFullDesc, String projectLink, LocalDate startDate, LocalDate endDate, Set<Role> roles, List<ProblemSolution> pns) {
        this.projectName = projectName;
        this.image = image;
        this.projectDesc = projectDesc;
        this.projectFullDesc = projectFullDesc;
        this.projectLink = projectLink;
        this.startDate = startDate;
        this.endDate = endDate;
        this.roles = roles;
        this.pns = pns;
    }
}
