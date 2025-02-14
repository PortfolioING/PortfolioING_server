package com.example.PING.portfolio.entity;

import com.example.PING.component.entity.Component;
import com.example.PING.domain.entity.Domain;
import com.example.PING.like.entity.Like;
import com.example.PING.scrap.entity.Scrap;
import com.example.PING.template.entity.Template;
import com.example.PING.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

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
    @JoinColumn(name = "component_id")
    private Component component;

    @ManyToOne
    @JoinColumn(name = "like_id")
    private Like like;

    @ManyToOne
    @JoinColumn(name = "scrap_id")
    private Scrap scrap;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private String titleImg;

    private List<TechStack> techStack;

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
    public Portfolio(User user, Template template, Component component) {
        this.user = user;
        this.template = template;
        this.component = component;
    }

//    public void updatePortfolioTemplate(Template template) {
//        this.template = template;
//    }

    public void updatePortfolioTitleImg(String titleImg) {
        this.titleImg = titleImg;
    }
}
