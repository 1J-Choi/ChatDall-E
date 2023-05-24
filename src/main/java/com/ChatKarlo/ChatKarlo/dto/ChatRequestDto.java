package com.ChatKarlo.ChatKarlo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChatRequestDto {
    private String model;
    private List<MessageDto> messageDtos;
    private int n;
    private double temperature;

    public ChatRequestDto(String model, String prompt){
        this.model = model;

        this.messageDtos = new ArrayList<>();
        this.messageDtos.add(new MessageDto("user", prompt));
    }
}
