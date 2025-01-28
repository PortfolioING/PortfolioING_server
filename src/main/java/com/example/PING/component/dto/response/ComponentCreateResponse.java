package com.example.PING.component.dto.response;

import com.example.PING.component.entity.Component;

public record ComponentCreateResponse (

        Long component_id,

        Long portfolio_id,

        Component.ComponentTag tag,

        Long parent_component_id,

        Long component_style_id

) {
}
