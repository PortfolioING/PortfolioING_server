package com.example.PING.component.service;

import com.example.PING.component.dto.request.ComponentCreateRequest;
import com.example.PING.component.dto.request.ComponentUpdateRequest;
import com.example.PING.component.dto.response.ComponentCreateResponse;
import com.example.PING.component.dto.response.ComponentUpdateResponse;
import com.example.PING.component.entity.Component;
import com.example.PING.component.repository.ComponentRepository;
import com.example.PING.portfolio.entity.Portfolio;
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

    public Optional<Component> getComponentById(Long id) {
        return componentRepository.findById(id);
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

    public ComponentUpdateResponse updateComponent(Long id, ComponentUpdateRequest requestDto) {

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

        return new ComponentUpdateResponse(
                savedComponent.getPortfolio().getPortfolioId(),
                savedComponent.getTag(),
                savedComponent.getParentComponent().getComponentId(),
                savedComponent.getChildComponents().stream()
                                .map(Component::getComponentId)// Component 객체에서 ID만 추출
                                        .collect(Collectors.toList()), // 결과를 List<Long>으로 수집
                savedComponent.getComponentStyleId()
        );
    }

    public void deleteComponent(Long id) {
        componentRepository.deleteById(id);
    }
}
