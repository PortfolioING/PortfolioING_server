package com.example.PING.dto;

import com.example.PING.entity.Domain;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DomainRequestDto {
    private Long portfolio_id;  // Portfolio ID
    private String domain;
}
