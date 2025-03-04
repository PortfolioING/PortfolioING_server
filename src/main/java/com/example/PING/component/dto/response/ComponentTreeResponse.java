package com.example.PING.component.dto.response;

import com.example.PING.component.entity.Component;

import java.util.List;

public record ComponentTreeResponse(

        Long componentId,

        Long portfolio_id,

        Component.ComponentTag tag,

        Long parent_component_id,

        List<ComponentTreeResponse> children,

        Long component_style_id

) {
    public static ComponentTreeResponse from(Component component,  List<ComponentTreeResponse> children) {
        return new ComponentTreeResponse(
                component.getComponentId(),
                component.getPortfolio().getPortfolioId(),
                component.getTag(),
                component.getParentComponent() != null ? component.getParentComponent().getComponentId() : null,
                children,
                component.getComponentStyleId()
        );
    }
}
