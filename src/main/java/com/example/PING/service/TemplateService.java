package com.example.PING.service;

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
        Template template = new Template();
        template.setName(templateRequestDto.getName());
        template.setDescription(templateRequestDto.getDescription());
        return convertToResponseDto(templateRepository.save(template));
    }

    @Transactional(readOnly = true)
    public List<TemplateResponseDto> getAllTemplates() {
        return templateRepository.findAll().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TemplateResponseDto getTemplateById(Long templateId) {
        return convertToResponseDto(templateRepository.findById(templateId).orElse(null));
    }

    @Transactional
    public void deleteTemplate(Long templateId) {
        templateRepository.deleteById(templateId);
    }

    private TemplateResponseDto convertToResponseDto(Template template) {
        TemplateResponseDto dto = new TemplateResponseDto();
        dto.setTemplateId(template.getTemplateId());
        dto.setName(template.getName());
        dto.setDescription(template.getDescription());
        return dto;
    }
}
