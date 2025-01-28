package com.example.PING.component.controller;

import com.example.PING.component.dto.request.ComponentCreateRequest;
import com.example.PING.component.dto.request.ComponentRequest;
import com.example.PING.component.dto.response.ComponentCreateResponse;
import com.example.PING.component.dto.response.ComponentResponse;
import com.example.PING.component.entity.Component;
import com.example.PING.component.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // (컴포넌트 생성) 새 컴포넌트 생성
    @PostMapping
    public ResponseEntity<ComponentCreateResponse> createComponent(@RequestBody ComponentCreateRequest requestDto) {
        ComponentCreateResponse response = componentService.createComponent(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // (컴포넌트 수정) 컴포넌트 수정
    @PutMapping("/{component_id}")
    public ResponseEntity<ComponentResponse> updateComponent(
            @PathVariable("component_id") Long componentId,
            @RequestBody ComponentRequest requestDto) {
        ComponentResponse response = componentService.updateComponent(componentId, requestDto);
        return ResponseEntity.ok(response);
    }

    // (컴포넌트 조회) 컴포넌트 조회
    @GetMapping
    public ResponseEntity<ComponentResponse> getComponentById(@RequestParam("component_id") Long componentId) {
        ComponentResponse response = componentService.getComponentById(componentId);
        return ResponseEntity.ok(response);
    }

    // (컴포넌트 삭제) 컴포넌트 삭제
    @DeleteMapping("/{component_id}")
    public ResponseEntity<Map<String, String>> deleteComponent(@PathVariable("component_id") Long componentId) {
        componentService.deleteComponent(componentId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Component deleted successfully");
        return ResponseEntity.ok(response);
    }
}
