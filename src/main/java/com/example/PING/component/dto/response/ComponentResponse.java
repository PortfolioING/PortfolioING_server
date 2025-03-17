package com.example.PING.component.dto.response;

import com.example.PING.component.entity.Component;

import java.util.List;
import java.util.stream.Collectors;

public record ComponentResponse(

        Long portfolio_id,

        Component.ComponentTag tag,

        Long parent_component_id,

        List<Long> child_component_id,

        Long component_style_id

) {
    public static ComponentResponse from(Component component) {
        if(component.getParentComponent() != null) {
            return new ComponentResponse (
                    component.getPortfolio().getPortfolioId(),
                    component.getTag(),
                    component.getParentComponent().getComponentId(),
                    component.getChildComponents().stream()
                            .map(Component::getComponentId)// Component 객체에서 ID만 추출
                            .collect(Collectors.toList()), // 결과를 List<Long>으로 수집
                    component.getComponentStyleId()
            );
        }
        else {
            return new ComponentResponse (
                    component.getPortfolio().getPortfolioId(),
                    component.getTag(),
                    null,
                    component.getChildComponents().stream()
                            .map(Component::getComponentId)// Component 객체에서 ID만 추출
                            .collect(Collectors.toList()), // 결과를 List<Long>으로 수집
                    component.getComponentStyleId()
            );
        }
    }
}
