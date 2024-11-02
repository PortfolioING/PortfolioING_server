package com.example.PING.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long portfolioId;

    // optional = true: request에서 제외 가능
    @OneToOne(optional = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "domain_id")
    private Domain domain;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private Template template;

    // CascadeType.ALL: Survey 영속성 전이
    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @Column(nullable = false)
    private String title;

    private String description;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    @Builder
    public Portfolio(User user, Template template, Survey survey, String title, String description) {
        this.user = user;
        this.template = template;
        this.survey = survey;
        this.title = title;
        this.description = description;
    }

    // 내용 수정 method
    public void updatePortfolioContents(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
