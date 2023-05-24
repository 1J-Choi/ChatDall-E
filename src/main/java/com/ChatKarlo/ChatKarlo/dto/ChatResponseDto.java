package com.ChatKarlo.ChatKarlo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ChatResponseDto {
    private List<Choice> choices;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Choice{
        private int index;
        private MessageDto messageDto;
    }
}
