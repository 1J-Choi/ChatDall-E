package com.ChatKarlo.ChatKarlo.controller;

import com.ChatKarlo.ChatKarlo.dto.ChatRequestDto;
import com.ChatKarlo.ChatKarlo.dto.ChatResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
@RequiredArgsConstructor
public class ChatController {
    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    @GetMapping("/chat")
    public String chat(@RequestParam String prompt) {
        // create a request
        ChatRequestDto request = new ChatRequestDto(model, prompt);

        // call the API
        ChatResponseDto response = restTemplate.postForObject(apiUrl, request, ChatResponseDto.class);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return "No response";
        }

        // return the first response
        return response.getChoices().get(0).getMessageDto().getContent();
    }
}
