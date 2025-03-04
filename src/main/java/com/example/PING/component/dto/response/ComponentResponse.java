package com.example.PING.component.dto.response;

import com.example.PING.component.entity.Component;

import java.util.List;
import java.util.stream.Collectors;

public record ComponentResponse(

        Long portfolio_id,

        Component.ComponentTag tag,

        Long parent_component_id,

        List<ComponentResponse> children,

        Long component_style_id

) {
    public static ComponentResponse from(Component component) {
        return new ComponentResponse (
                component.getPortfolio().getPortfolioId(),
                component.getTag(),
                component.getParentComponent() != null ? component.getParentComponent().getComponentId() : null,
                component.getChildComponents().stream()
                        .map(ComponentResponse::from)  // ComponentResponse 객체로 변환
                        .collect(Collectors.toList()),
                component.getComponentStyleId()
        );
    }
}
/*
* component.getComponentId(),
                component.getPortfolio().getPortfolioId(),
                component.getTag(),
                component.getParentComponent() != null ? component.getParentComponent().getComponentId() : null,
                component.getChildComponents().stream()
                        .map(ComponentResponse::from)  // ComponentResponse 객체로 변환
                        .collect(Collectors.toList()),
                component.getComponentStyleId()
* */