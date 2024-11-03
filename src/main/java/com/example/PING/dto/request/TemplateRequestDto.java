package com.example.PING.dto.request;

import com.example.PING.entity.Template;
import lombok.Data;

@Data
public class TemplateRequestDto {
    private String name;
    private String description;
}
