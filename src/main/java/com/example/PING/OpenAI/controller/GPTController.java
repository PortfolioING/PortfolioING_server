package com.example.PING.OpenAI.controller;

import com.example.PING.OpenAI.dto.ChatGPTResponse;
import com.example.PING.OpenAI.service.GPTService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/gpt")
public class GPTController {
    private final GPTService gptService;
    @GetMapping("/chat")
    public String chat(@RequestParam("component_id") Long componentId, @RequestParam(name = "prompt") String prompt) {
        ChatGPTResponse chatGPTResponse = gptService.chat(componentId, prompt);
        return chatGPTResponse.getChoices().get(0).getMessage().getContent();
    }

}

