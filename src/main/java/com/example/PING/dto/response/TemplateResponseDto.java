package com.example.PING.dto.response;

import com.example.PING.entity.Template;
import lombok.Builder;
import lombok.Data;

@Data
public class TemplateResponseDto {
    private Long templateId;
    private String name;
    private String description;

    @Builder
    public TemplateResponseDto(Template template) {
        this.templateId = template.getTemplateId();
        this.name = template.getName();
        this.description = template.getDescription();
    }
}
