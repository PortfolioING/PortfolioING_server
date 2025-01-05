package com.example.PING.dto.response;

import com.example.PING.entity.Template;

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
