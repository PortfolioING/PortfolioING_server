package com.example.PING.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long surveyId;

    // optional = true: request에서 제외 가능
    @OneToOne (optional = true, mappedBy = "survey")
    private Portfolio portfolio;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
    private List<Project> projects = new ArrayList<>();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String PR;

    @Column(nullable = false)
    private String pic;

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    @Builder
    public Survey(List<Project> projects, String name, String PR, String pic) {
        this.projects = projects;
        this.name = name;
        this.PR = PR;
        this.pic = pic;
    }
}

