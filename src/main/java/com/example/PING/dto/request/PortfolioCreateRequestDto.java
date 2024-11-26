package com.example.PING.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class PortfolioCreateRequestDto {
    @NotNull(message = "User ID is required.")
    private Long user_id;

    @NotNull(message = "Survey ID is required.")
    private Long survey_id;

    private String title;
    private String description;
    private String image;

}
