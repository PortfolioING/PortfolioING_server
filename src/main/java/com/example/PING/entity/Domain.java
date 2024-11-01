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

    // unique: 중복되는 domain url이 입력되는 것을 방지
    @Column(nullable = false, unique = true)
    private String domain;

    // updatable = false: 생성 이후 수정 방지
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}

