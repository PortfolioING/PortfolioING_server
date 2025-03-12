package com.example.PING.portfolio.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioPageRequest {
    private int page = 0;
    private int size = 50;      // 한 페이지에 포함되는 최대 Portfolio 개수
    private String sort;        // "latest" or "likes"
}
