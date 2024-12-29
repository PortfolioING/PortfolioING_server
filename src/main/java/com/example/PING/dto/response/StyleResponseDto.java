package com.example.PING.dto.response;

import com.example.PING.entity.Style;

public class StyleResponseDto {
    private Long id;
    private String mainColor;
    private String subColor;
    private String backgroundColor;

    public StyleResponseDto(Long id, String mainColor, String subColor, String backgroundColor) {
        this.id = id;
        this.mainColor = mainColor;
        this.subColor = subColor;
        this.backgroundColor = backgroundColor;
    }

    public static StyleResponseDto from(Style style) {
        return new StyleResponseDto(
                style.getStyleId(),
                style.getMainColor(),
                style.getSubColor(),
                style.getBackgroundColor()
        );
    }
}
