package com.example.PING.service;

import com.example.PING.dto.request.TemplateRequest;
import com.example.PING.dto.response.TemplateResponse;
import com.example.PING.entity.Template;
import com.example.PING.error.ResourceNotFoundException;
import com.example.PING.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TemplateService {
    private final TemplateRepository templateRepository;

    @Transactional
    public TemplateResponse createTemplate(TemplateRequest templateRequest) {
        Template template = Template.builder()
                .name(templateRequest.name())
                .description(templateRequest.description())
                .build();
        return TemplateResponse.from(templateRepository.save(template));
    }

    // (템플릿 목록 조회) 모든 템플릿 조회
    @Transactional(readOnly = true)
    public List<TemplateResponse> getAllTemplates() {
        return templateRepository.findAll().stream()
                .map(TemplateResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TemplateResponse getTemplateById(Long templateId) {
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new ResourceNotFoundException("Template not found with ID: " + templateId));
        return TemplateResponse.from(template);
    }

    @Transactional
    public void deleteTemplate(Long templateId) {
        templateRepository.deleteById(templateId);
    }
}
