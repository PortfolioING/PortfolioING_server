package com.example.PING.portfolio.dto.response;

import com.example.PING.portfolio.entity.Style;

public record StyleResponse(
        Long id,
        String mainColor,
        String subColor,
        String backgroundColor
) {
    public static StyleResponse from(Style style) {
        return new StyleResponse(
                style.getStyleId(),
                style.getMainColor(),
                style.getSubColor(),
                style.getBackgroundColor()
        );
    }
}