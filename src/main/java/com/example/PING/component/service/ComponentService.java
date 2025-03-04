package com.example.PING.component.service;

import com.example.PING.component.dto.request.*;
import com.example.PING.component.dto.response.*;
import com.example.PING.component.entity.Component;
import com.example.PING.portfolio.entity.Portfolio;
import com.example.PING.component.repository.ComponentRepository;
import com.example.PING.portfolio.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComponentService {

    private final ComponentRepository componentRepository;
    private final PortfolioRepository portfolioRepository;

    @Autowired
    public ComponentService(ComponentRepository componentRepository, PortfolioRepository portfolioRepository) {
        this.componentRepository = componentRepository;
        this.portfolioRepository = portfolioRepository;
    }

    public List<Component> getAllComponents() {
        return componentRepository.findAll();
    }

    @Transactional
    public ComponentCreateResponse createComponent(ComponentCreateRequest requestDto) {

        Portfolio portfolio = portfolioRepository.findById(requestDto.portfolio_id())
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found with ID: " + requestDto.portfolio_id()));

        Component parentComponent = componentRepository.findById(requestDto.parent_component_id())
                .orElseThrow(() -> new IllegalArgumentException("Parent component not found with ID: " + requestDto.parent_component_id()));

        Component component = Component.builder()
                .portfolio(portfolio)
                .tag(requestDto.tag())
                .parentComponent(parentComponent)
                .componentStyleId(requestDto.component_style_id())
                .build();

        Component savedComponent = componentRepository.save(component);

        return new ComponentCreateResponse(
                savedComponent.getComponentId(),
                savedComponent.getPortfolio().getPortfolioId(),
                savedComponent.getTag(),
                savedComponent.getParentComponent().getComponentId(),
                savedComponent.getComponentStyleId()
        );
    }

    @Transactional
    public ComponentResponse updateComponent(Long id, ComponentRequest requestDto) {

        Component component = componentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Component not found with id " + id));

        Portfolio portfolio = portfolioRepository.findById(requestDto.portfolio_id())
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found with ID: " + requestDto.portfolio_id()));

        Component parentComponent = componentRepository.findById(requestDto.parent_component_id())
                .orElseThrow(() -> new IllegalArgumentException("Parent component not found with ID: " + requestDto.parent_component_id()));

        List<Component> childComponents = requestDto.child_component_id().stream()
                .map(childId -> componentRepository.findById(childId)
                        .orElseThrow(() -> new RuntimeException("Child component not found with ID: " + childId)))
                .collect(Collectors.toList());

        component.setPortfolio(portfolio);
        component.setTag(requestDto.tag());
        component.setParentComponent(parentComponent);
        component.setChildComponents(childComponents);
        component.setComponentStyleId(requestDto.component_style_id());

        Component savedComponent = componentRepository.save(component);

        return ComponentResponse.from(savedComponent);
    }

    @Transactional
    public ComponentResponse getComponentById(Long componentId) {
        Component component = componentRepository.findById(componentId)
                .orElseThrow(() -> new IllegalArgumentException("Component not found with ID : " + componentId));
        return ComponentResponse.from(component);
    }

    @Transactional
    public void deleteComponent(Long componentId) {
        // findbyid -> delete 보다 deletebyid를 사용하는 것이 성능 최적화
        componentRepository.deleteById(componentId);
    }

    @Transactional(readOnly = true)
    public ComponentTreeResponse getComponentTree(Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new RuntimeException("Portfolio not found with id " + portfolioId));
        Component rootComponent = portfolio.getComponent();
        if (rootComponent == null) {
            throw new RuntimeException("Root component not found for portfolio id " + portfolioId);
        }

        return buildComponentTree(rootComponent);
    }

    private ComponentTreeResponse buildComponentTree(Component component) {
        List<ComponentTreeResponse> children = component.getChildComponents().stream()
                .map(this::buildComponentTree)  // 재귀적으로 자식 컴포넌트 처리
                .collect(Collectors.toList());

        return ComponentTreeResponse.from(component, children);

    }

}



