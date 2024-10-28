package com.example.PING.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long templateId;

    @OneToMany(mappedBy = "template")
    private List<Portfolio> portfolios = new ArrayList<>();

    @Column(nullable = false)
    private String name;

    private String description;
}

