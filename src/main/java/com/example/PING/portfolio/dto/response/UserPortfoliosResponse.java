package com.example.PING.portfolio.dto.response;

import java.util.List;

public record UserPortfoliosResponse (
        Long userId,
        List<PortfolioResponse> portfolios
){
}
