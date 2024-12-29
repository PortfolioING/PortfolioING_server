package com.example.PING.dto.response;

import com.example.PING.entity.Template;

public record TemplateResponseDto (
        Long templateId,
        String name,
        String description
){
    public static TemplateResponseDto from(Template template) {
        return new TemplateResponseDto(
                template.getTemplateId(),
                template.getName(),
                template.getDescription()
        );
    }
}
