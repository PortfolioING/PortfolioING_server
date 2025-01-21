package com.example.PING.template.controller;

import com.example.PING.template.dto.response.TemplateResponse;
import com.example.PING.template.service.TemplateService;
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
    public ResponseEntity<Map<String, List<TemplateResponse>>> getAllTemplates() {
        List<TemplateResponse> templates = templateService.getAllTemplates();
        return ResponseEntity.ok(Map.of("templates", templates));
    }

    // 특정 템플릿 상세 조회
    @GetMapping("/{template_id}")
    public ResponseEntity<TemplateResponse> getTemplateById(@PathVariable("template_id") Long templateId) {
        TemplateResponse response = templateService.getTemplateById(templateId);
        return ResponseEntity.ok(response);
    }
}
