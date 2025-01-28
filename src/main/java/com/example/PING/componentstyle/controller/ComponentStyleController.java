package com.example.PING.componentstyle.controller;

import com.example.PING.componentstyle.service.ComponentStyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/component_styles/")
public class ComponentStyleController {

    private final ComponentStyleService componentStyleService;

    @Autowired
    public ComponentStyleController(ComponentStyleService componentStyleService) {
        this.componentStyleService = componentStyleService;
    }
}
