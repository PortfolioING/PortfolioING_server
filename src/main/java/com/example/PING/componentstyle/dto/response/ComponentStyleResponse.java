package com.example.PING.componentstyle.dto.response;

import com.example.PING.componentstyle.entity.ComponentStyle;

public record ComponentStyleResponse(

        Long componentStyleId,

        Boolean bold,

        Boolean italic,

        Boolean underscore,

        Boolean strikethrough,

        Long size,

        String textColor,

        String backgroundColor
) {
    public static ComponentStyleResponse from(ComponentStyle componentStyle) {

        return new ComponentStyleResponse(
                componentStyle.getComponentStyleId(),
                componentStyle.getBold(),
                componentStyle.getItalic(),
                componentStyle.getUnderscore(),
                componentStyle.getStrikethrough(),
                componentStyle.getSize(),
                componentStyle.getTextColor(),
                componentStyle.getBackgroundColor()
        );
    }
}
