package com.example.PING.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable // Embeddable 클래스
public class ProblemSolution {
    private String problem;
    private String solution;
}
