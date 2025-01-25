package com.example.PING.user.dto.response;

import com.example.PING.user.entity.User;

import java.util.List;

public record UserPortfolioIdListResponse(
        List<Long> portfolioIdList
) {
    public static UserPortfolioIdListResponse from(User user) {
        return new UserPortfolioIdListResponse(
                user.getPortfolioIds()
        );
    }
}
