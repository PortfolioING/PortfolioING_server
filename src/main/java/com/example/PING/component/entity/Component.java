package com.example.PING.component.entity;

import com.example.PING.portfolio.entity.Portfolio;
import com.example.PING.project.entity.Project;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Component {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long componentId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio; // 연관된 포트폴리오

    @Enumerated(EnumType.STRING)
    private ComponentTag tag; // Component 유형에 대한 태그

    @ManyToOne
    @JoinColumn(name = "parent_component_id")
    private Component parentComponent; // 부모 컴포넌트

    @OneToMany(mappedBy = "parentComponent", cascade = CascadeType.ALL)
    private List<Component> childComponents = new ArrayList<>(); // 자식 컴포넌트

    @Column(length = 255)
    private String text; // 글 내용

    @Column(name = "componentStyleId")
    private Long componentStyleId; // 컴포넌트 스타일

    @Builder
    public Component(Portfolio portfolio, Project project, ComponentTag tag,
                     Component parentComponent, Long componentStyleId) {
        this.portfolio = portfolio;
        this.tag = tag;
        this.parentComponent = parentComponent;
        this.componentStyleId = componentStyleId;
    }

    // ComponentTag Enum 정의
    public enum ComponentTag {
        TITLE,
        INTRODUCE,
        CAREER_TITLE,
        CAREER_DESC,
        CAREER_LINK,
        IMG,
        STACK,
        PROJECT_TITLE,
        PROJECT_DESC,
        PROJECT_LINK,
        TEXT,
        PARENT

    }
}
