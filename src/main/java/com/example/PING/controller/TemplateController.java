package com.example.PING.controller;

import com.example.PING.dto.response.TemplateResponseDto;
import com.example.PING.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/templates")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateService templateService;

    // 모든 템플릿 조회
    @GetMapping
    public ResponseEntity<Map<String, List<TemplateResponseDto>>> getAllTemplates() {
        List<TemplateResponseDto> templates = templateService.getAllTemplates();
        return ResponseEntity.ok(Map.of("templates", templates));
    }

    // 특정 템플릿 상세 조회
    @GetMapping("/{template_id}")
    public ResponseEntity<TemplateResponseDto> getTemplateById(@PathVariable("template_id") Long templateId) {
        TemplateResponseDto response = templateService.getTemplateById(templateId);
        return ResponseEntity.ok(response);
    }
}
