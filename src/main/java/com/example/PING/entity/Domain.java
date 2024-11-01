package com.example.PING.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long domainId;

    @OneToOne (mappedBy = "domain")
    private Portfolio portfolio;

    @Column(nullable = false, unique = true)
    private String domain;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}

