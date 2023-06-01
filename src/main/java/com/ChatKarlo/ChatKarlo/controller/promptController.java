package com.ChatKarlo.ChatKarlo.controller;

import com.ChatKarlo.ChatKarlo.dto.PromptFormDto;
import com.ChatKarlo.ChatKarlo.service.MemberService;
import com.ChatKarlo.ChatKarlo.service.PromptService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@AllArgsConstructor
public class promptController {
    private final PromptService promptService;
    private final MemberService memberService;

    @GetMapping("/prompt")
    public String promptDtl(Model model, Principal principal){
        Long nowMemberId = memberService.findMemberId(principal.getName());
        PromptFormDto promptFormDto = promptService.findPromptFormDto(nowMemberId);
        model.addAttribute("promptFormDto", promptFormDto);
        return "prompt/promptForm";
    }

    @PostMapping("/prompt")
    public String promptNew(@Valid PromptFormDto promptFormDto, BindingResult bindingResult, Model model, Principal principal){
        if (bindingResult.hasErrors()){
            return "prompt/promptForm";
        }

        String email = principal.getName();

        try{
            promptService.savePrompt(promptFormDto, email);
        }catch (Exception e){
            model.addAttribute("errorMessage", "이미지 생성 중 에러가 발생했습니다.");
            return "prompt/promptForm";
        }

        return "redirect:/";

    }
}
