package com.example.PING.survey.entity;

import com.example.PING.portfolio.entity.Portfolio;
import com.example.PING.project.entity.Project;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long surveyId;

    // optional = true: request에서 제외 가능
//    @OneToOne (optional = true, mappedBy = "survey")
//    private Portfolio portfolio;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL)
    private List<Project> projects = new ArrayList<>();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String introduce;

    @Column(nullable = false)
    private String profile;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    @Getter private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @Getter private LocalDateTime updatedAt;

//    public void savePortfolioToSurvey(Portfolio portfolio) {
//        this.portfolio = portfolio;
//    }

//    public void setPortfolioNull() {
//        this.portfolio = null;
//    }

    @Builder
    public Survey() {
        this.projects = projects;
        this.name = name;
        this.introduce = introduce;
        this.profile = profile;
    }
}

