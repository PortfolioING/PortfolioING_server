package com.example.PING.template.dto.response;

import com.example.PING.template.entity.Template;

public record TemplateResponse(
        Long templateId,
        String name,
        String description
){
    public static TemplateResponse from(Template template) {
        return new TemplateResponse(
                template.getTemplateId(),
                template.getName(),
                template.getDescription()
        );
    }
}
