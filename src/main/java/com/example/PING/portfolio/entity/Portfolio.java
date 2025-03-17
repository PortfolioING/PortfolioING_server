package com.example.PING.portfolio.entity;

import com.example.PING.component.entity.Component;
import com.example.PING.domain.entity.Domain;
import com.example.PING.likes.entity.Likes;
import com.example.PING.scrap.entity.Scrap;
import com.example.PING.template.entity.Template;
import com.example.PING.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<Component> components = new ArrayList<>();

    @Setter @Getter
    private Long rootComponentId; // 최상단 컴포넌트를 저장하는 필드

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<Likes> likes = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)
    private List<Scrap> scraps = new ArrayList<>();

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
        addComponent(component);
    }

    public void addComponent(Component component) {
        if(component != null) {
            components.add(component);
            component.setPortfolio(this); // 연관 관계 설정
        }
    }

    public Component getRootComponent() {
        return components.stream()
                .filter(component -> component.getParentComponent() == null)
                .findFirst()
                .orElse(null);
    }

    public List<Component> getComponents() {
        return components;
    }

//    public void updatePortfolioTemplate(Template template) {
//        this.template = template;
//    }


    public void updatePortfolioTitleImg(String titleImg) {
        this.titleImg = titleImg;
    }
}
