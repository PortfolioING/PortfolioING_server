package com.example.PING.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long portfolioId;

    // optional = true: request에서 제외 가능
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "domain_id")
    private Domain domain;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private Template template;

    @Column(name = "main_color", length = 7) // #RRGGBB
    private String mainColor;

    @Column(name = "sub_color", length = 7)
    private String subColor;

    @Column(name = "background_color", length = 7)
    private String backgroundColor;

    // CascadeType.ALL: Survey 영속성 전이
    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "survey_id")
//    @JsonIgnore // 이 필드를 JSON 직렬화에서 제외
    private Survey survey;

    @Column(nullable = false)
    private String title;

    private String description;

    private String image;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Long getId() {
        return portfolioId;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    @Builder
    public Portfolio(User user, Survey survey, String title, String description, String image) {
        this.user = user;
        this.survey = survey;
        this.title = title;
        this.description = description;
        this.image = image;
    }


    // 내용 수정 method
    public void updatePortfolioContents(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void updatePortfolioTemplate(Template template) {
        this.template = template;
    }
}
