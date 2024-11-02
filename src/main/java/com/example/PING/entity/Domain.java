package com.example.PING.entity;

import com.example.PING.dto.DomainRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
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
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public Domain(Portfolio portfolio, String domain, LocalDateTime createdAt) {
        this.portfolio = portfolio;
        this.domain = domain;
        this.createdAt = createdAt;
    }

}

