package com.example.PING.component.dto.request;

import com.example.PING.component.entity.Component;

import java.util.List;

public record ComponentUpdateRequest(

        Long portfolio_id,

        Component.ComponentTag tag,

        Long parent_component_id,

        List<Long> child_component_id,

        Long component_style_id

) {
}
