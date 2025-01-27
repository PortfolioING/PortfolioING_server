package com.example.PING.component.controller;

import com.example.PING.component.dto.request.ComponentCreateRequest;
import com.example.PING.component.dto.response.ComponentCreateResponse;
import com.example.PING.component.entity.Component;
import com.example.PING.component.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/components")
public class ComponentController {

    private final ComponentService componentService;

    @Autowired
    public ComponentController(ComponentService componentService) {
        this.componentService = componentService;
    }

    @GetMapping
    public List<Component> getAllComponents() {
        return componentService.getAllComponents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Component> getComponentById(@PathVariable Long id) {
        return componentService.getComponentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ComponentCreateResponse> createComponent(@RequestBody ComponentCreateRequest requestDto) {
        ComponentCreateResponse response = componentService.createComponent(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Component> updateComponent(@PathVariable Long id, @RequestBody Component componentDetails) {
        return ResponseEntity.ok(componentService.updateComponent(id, componentDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComponent(@PathVariable Long id) {
        componentService.deleteComponent(id);
        return ResponseEntity.noContent().build();
    }
}
