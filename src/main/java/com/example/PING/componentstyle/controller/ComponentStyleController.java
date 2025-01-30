package com.example.PING.componentstyle.controller;

import com.example.PING.componentstyle.dto.request.ComponentStyleRequest;
import com.example.PING.componentstyle.dto.response.ComponentStyleResponse;
import com.example.PING.componentstyle.service.ComponentStyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/component_styles/")
public class ComponentStyleController {

    private final ComponentStyleService componentStyleService;

    @Autowired
    public ComponentStyleController(ComponentStyleService componentStyleService) {
        this.componentStyleService = componentStyleService;
    }

    // (컴포넌트 스타일 생성)
    @PostMapping
    public ResponseEntity<ComponentStyleResponse> createComponentStyle(@RequestBody ComponentStyleRequest requestDto) {
        ComponentStyleResponse response = componentStyleService.createComponentStyle(requestDto);
        return ResponseEntity.ok(response);
    }

    // (컴포넌트 스타일 수정)
    @PutMapping("/{component_style_id}")
    public ResponseEntity<ComponentStyleResponse> updateComponentStyle(
            @PathVariable("component_style_id") Long componentStyleId,
            @RequestBody ComponentStyleRequest requestDto) {
        ComponentStyleResponse response = componentStyleService.updateComponentStyle(componentStyleId, requestDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{component_style_id}")
    public ResponseEntity<ComponentStyleResponse> getComponentStyle(
            @PathVariable("component_style_id") Long componentStyleId) {
        ComponentStyleResponse response = componentStyleService.getComponentStyleById(componentStyleId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{component_style_id}")
    public ResponseEntity<Map<String, String>> deleteComponentStyle(
            @PathVariable("component_style_id") Long componentStyleId) {
        componentStyleService.deleteComponentStyleId(componentStyleId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "ComponentStyle deleted successfully");
        return ResponseEntity.ok(response);
    }
}
