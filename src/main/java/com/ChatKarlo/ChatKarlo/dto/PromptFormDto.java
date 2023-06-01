package com.ChatKarlo.ChatKarlo.dto;

import com.ChatKarlo.ChatKarlo.entity.Member;
import com.ChatKarlo.ChatKarlo.entity.Prompt;
import lombok.Builder;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class PromptFormDto {

    private Long id;

    private String url;

    @NotNull(message = "이미지 생성을 위한 문장을 입력하세요.")
    private String beforeWords;
    private static ModelMapper modelMapper = new ModelMapper();

    public Prompt createPrompt() {
        return modelMapper.map(this, Prompt.class);
    }
    public static PromptFormDto of(Prompt prompt) {
        return modelMapper.map(prompt, PromptFormDto.class);
    }

}
