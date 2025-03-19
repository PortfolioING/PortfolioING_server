package com.example.PING.OpenAI.service;

import com.example.PING.OpenAI.dto.ChatGPTRequest;
import com.example.PING.OpenAI.dto.ChatGPTResponse;
import com.example.PING.component.entity.Component;
import com.example.PING.component.repository.ComponentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GPTService {

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiURL;

    private final RestTemplate template;
    private final ComponentRepository componentRepository;

    public ChatGPTResponse chat(Long componentId, String prompt) {
        // GPT에 요청하는 prompt에 들어갈 내용의 확장을 위해 Component 객체를 사용한다.
        Component component = componentRepository.findById(componentId)
                .orElseThrow(() -> new RuntimeException("Component not found with id " + componentId));

        String reqStr = component.getTag() + prompt;
        ChatGPTRequest request = new ChatGPTRequest(model, reqStr);
        return template.postForObject(apiURL, request, ChatGPTResponse.class);
    }
}
