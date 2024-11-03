package com.example.PING.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserPortfoliosResponse {
    private Long userId;
    private List<PortfolioResponseDto> portfolios;
}
