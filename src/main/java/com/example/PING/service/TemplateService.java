package com.example.PING.service;

import com.example.PING.dto.response.PortfolioResponseDto;
import com.example.PING.dto.request.TemplateRequestDto;
import com.example.PING.dto.response.TemplateResponseDto;
import com.example.PING.entity.Template;
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
    public TemplateResponseDto createTemplate(TemplateRequestDto templateRequestDto) {
        Template template = Template.builder()
                .name(templateRequestDto.name())
                .description(templateRequestDto.description())
                .build();
        return TemplateResponseDto.from(templateRepository.save(template));
    }

    // (템플릿 목록 조회) 모든 템플릿 조회
    @Transactional(readOnly = true)
    public List<TemplateResponseDto> getAllTemplates() {
        return templateRepository.findAll().stream()
                .map(TemplateResponseDto::from)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TemplateResponseDto getTemplateById(Long templateId) {
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new IllegalArgumentException("Template not found with ID: " + templateId));
        return TemplateResponseDto.from(template);
    }

    @Transactional
    public void deleteTemplate(Long templateId) {
        templateRepository.deleteById(templateId);
    }
}
