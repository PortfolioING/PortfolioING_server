package com.example.PING.dto.response;

import java.util.List;

public record UserPortfoliosResponse (
        Long userId,
        List<PortfolioResponse> portfolios
){
}
