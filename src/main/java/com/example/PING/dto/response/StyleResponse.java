package com.example.PING.dto.response;

import com.example.PING.entity.Style;

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