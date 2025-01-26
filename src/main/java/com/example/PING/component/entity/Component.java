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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    private Project project; // 연관된 프로젝트

    @Enumerated(EnumType.STRING)
    private ComponentTag tag; // Component 유형에 대한 태그

    private Long arrangeId; // 컴포넌트 배치 조합 아이디

    @ManyToOne
    @JoinColumn(name = "parent_component_id")
    private Component parentComponent; // 부모 컴포넌트

    @OneToMany(mappedBy = "parentComponent", cascade = CascadeType.ALL)
    private List<Component> childComponents = new ArrayList<>(); // 자식 컴포넌트

    @Column(length = 255)
    private String content; // 글 내용

    @Builder
    public Component(Portfolio portfolio, Project project, ComponentTag tag,
                     Long arrangeId, Component parentComponent, List<Component> childComponents, String content) {
        this.portfolio = portfolio;
        this.project = project;
        this.tag = tag;
        this.arrangeId = arrangeId;
        this.parentComponent = parentComponent;
        this.childComponents = childComponents;
        this.content = content;
    }

    // ComponentTag Enum 정의
    public enum ComponentTag {
        BOARD,
        PORTFOLIO_TITLE,
        PROJECT_TITLE,
        DESC,
        FULL_DESC

    }
}
