package com.example.PING.user.dto.response;

import com.example.PING.portfolio.dto.response.PortfolioResponse;
import com.example.PING.portfolio.dto.response.StyleResponse;
import com.example.PING.portfolio.entity.Portfolio;
import com.example.PING.survey.dto.response.SurveyResponse;
import com.example.PING.user.entity.User;

import java.time.LocalDateTime;

public record UserSignUpResponse(
        Long userId,
        String name,
        String email,
        String nickname,
        String profilePic,
        LocalDateTime created_at,
        LocalDateTime updated_at
) {
    public static UserSignUpResponse from(User user) {
        return new UserSignUpResponse(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getNickname(),
                user.getProfilePic(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
