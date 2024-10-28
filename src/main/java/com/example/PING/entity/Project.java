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

    @ManyToOne
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    @Column(name = "project_name", length = 128, nullable = false)
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
}
