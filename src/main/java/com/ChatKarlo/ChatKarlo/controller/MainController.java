package com.ChatKarlo.ChatKarlo.controller;

import com.ChatKarlo.ChatKarlo.dto.PromptFormDto;
import com.ChatKarlo.ChatKarlo.service.PromptService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final PromptService promptService;

    @GetMapping(value = {"/"})
    public String main(Model model){
        return "main";
    }
}
