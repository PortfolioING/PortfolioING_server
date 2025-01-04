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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "style_id")
    private Style style;

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

    public void saveDomainToPortfolio(Domain domain) { // 생성한 도메인을 Portfolio에 저장할 때 사용
        this.domain = domain;
    }

    public void setDomainNull(){ // 도메인을 제거할 때 Portfolio의 도메인에 null값을 저장해놓기 위해 사용
        this.domain = null;
    }

    @Builder
    public Portfolio(User user, Style style, Survey survey, String title, String description, String image) {
        this.user = user;
        this.style = style;
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
