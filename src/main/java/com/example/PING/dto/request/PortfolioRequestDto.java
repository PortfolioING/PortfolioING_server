package com.example.PING.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter @Setter
@NoArgsConstructor
public class PortfolioRequestDto {

    @NotNull(message = "User ID is required.")
    private Long user_id;

    @NotNull(message = "Survey ID is required.")
    private Long survey_id;

    @NotNull(message = "Template ID is required.")
    private Long template_id;

    private String title;
    private String description;
    private String mainColor; // 컬러값 요소 포함
    private String subColor;
    private String backgroundColor;
}
