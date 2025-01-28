package com.example.PING.componentstyle.service;

import com.example.PING.componentstyle.repository.ComponentStyleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComponentStyleService {

    private final ComponentStyleRepository componentStyleRepository;

    @Autowired
    public ComponentStyleService(ComponentStyleRepository componentStyleRepository) {
        this.componentStyleRepository = componentStyleRepository;
    }
}
