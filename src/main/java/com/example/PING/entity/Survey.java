package com.example.PING.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long surveyId;

    @OneToOne (mappedBy = "survey")
    private Portfolio portfolio;

    @OneToMany(mappedBy = "survey")
    private List<Project> projects = new ArrayList<>();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String PR;

    @Column(nullable = false)
    private String pic;

    @Column(name = "created_at", updatable = false)
    @Getter @Setter private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @Getter private LocalDateTime updatedAt;
}

