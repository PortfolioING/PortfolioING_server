package com.example.PING.user.dto.response;

import com.example.PING.portfolio.dto.response.PortfolioResponse;

import java.util.List;

public record UserPortfoliosResponse (
        Long userId,
        List<PortfolioResponse> portfolios
){
}
