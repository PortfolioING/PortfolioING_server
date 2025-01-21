package com.example.PING.component.service;

import com.example.PING.component.entity.Component;
import com.example.PING.component.repository.ComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComponentService {

    private final ComponentRepository componentRepository;

    @Autowired
    public ComponentService(ComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }

    public List<Component> getAllComponents() {
        return componentRepository.findAll();
    }

    public Optional<Component> getComponentById(Long id) {
        return componentRepository.findById(id);
    }

    public Component createComponent(Component component) {
        return componentRepository.save(component);
    }

    public Component updateComponent(Long id, Component componentDetails) {
        Component component = componentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Component not found with id " + id));

        component.setPortfolio(componentDetails.getPortfolio());
        component.setProject(componentDetails.getProject());
        component.setTag(componentDetails.getTag());
        component.setArrangeId(componentDetails.getArrangeId());
        component.setParentComponent(componentDetails.getParentComponent());
        component.setChildComponents(componentDetails.getChildComponents());
        component.setContent(componentDetails.getContent());

        return componentRepository.save(component);
    }

    public void deleteComponent(Long id) {
        componentRepository.deleteById(id);
    }
}
