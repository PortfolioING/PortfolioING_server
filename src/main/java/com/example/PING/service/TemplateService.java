package com.example.PING.service;

import com.example.PING.dto.PortfolioResponseDto;
import com.example.PING.dto.TemplateRequestDto;
import com.example.PING.dto.TemplateResponseDto;
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
                .name(templateRequestDto.getName())
                .description(templateRequestDto.getDescription())
                .build();
        return convertToResponseDto(templateRepository.save(template));
    }

    // (템플릿 목록 조회) 모든 템플릿 조회
    @Transactional(readOnly = true)
    public List<TemplateResponseDto> getAllTemplates() {
        return templateRepository.findAll().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TemplateResponseDto getTemplateById(Long templateId) {
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new IllegalArgumentException("Template not found with ID: " + templateId));
        return convertToResponseDto(template);
    }

    @Transactional
    public void deleteTemplate(Long templateId) {
        templateRepository.deleteById(templateId);
    }

    private TemplateResponseDto convertToResponseDto(Template template) {
        return TemplateResponseDto.builder()
                .template(template)
                .build();
    }

}
