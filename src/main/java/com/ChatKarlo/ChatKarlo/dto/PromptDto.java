package com.ChatKarlo.ChatKarlo.dto;

import com.ChatKarlo.ChatKarlo.entity.Member;
import com.ChatKarlo.ChatKarlo.entity.Prompt;
import lombok.Builder;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
@Builder
public class PromptDto {
    private Long id;
    private String beforeWords;
    private String afterWords;
    private String url;
    private Member member;

    private static ModelMapper modelMapper = new ModelMapper();

    public Prompt createPrompt(){ return modelMapper.map(this, Prompt.class); }
    public static PromptDto of(Prompt prompt){ return modelMapper.map(prompt, PromptDto.class);}
}
