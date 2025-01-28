package com.example.PING.component.dto.request;

import com.example.PING.component.entity.Component;
import jakarta.validation.constraints.NotNull;

public record ComponentCreateRequest(

        @NotNull(message = "User ID is required.")
        Long portfolio_id,

        Component.ComponentTag tag,

        Long parent_component_id,

        Long component_style_id
) {
}
