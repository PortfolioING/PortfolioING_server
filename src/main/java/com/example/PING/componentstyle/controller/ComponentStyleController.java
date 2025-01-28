package com.example.PING.componentstyle.controller;

import com.example.PING.componentstyle.dto.request.ComponentStyleRequest;
import com.example.PING.componentstyle.dto.response.ComponentStyleResponse;
import com.example.PING.componentstyle.service.ComponentStyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
