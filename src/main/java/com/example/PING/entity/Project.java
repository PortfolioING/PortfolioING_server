package com.example.PING.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    @Setter
    @ManyToOne
    @JoinColumn(name = "survey_id", nullable = true)
    private Survey survey;

    @Column(name = "project_name", length = 128)
    private String projectName;

    @Column(name = "image", length = 128)
    private String image;

    @Column(name = "short_intro", length = 128)
    private String shortIntro;

    @Column(name = "long_intro", length = 128)
    private String longIntro;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "target", length = 128)
    private String target;

    @Column(name = "role", length = 128)
    private String role;

    @Column(name = "problem", length = 128)
    private String problem;

    @Column(name = "solution", length = 128)
    private String solution;

    @Column(name = "feedback", length = 128)
    private String feedback;

    @Builder
    public Project(String projectName, String image, String shortIntro, String longIntro, LocalDateTime date, String target, String role, String problem, String solution, String feedback) {
        this.projectName = projectName;
        this.image = image;
        this.shortIntro = shortIntro;
        this.longIntro = longIntro;
        this.date = date;
        this.target = target;
        this.role = role;
        this.problem = problem;
        this.solution = solution;
        this.feedback = feedback;
    }
}
