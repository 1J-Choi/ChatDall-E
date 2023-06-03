package com.ChatKarlo.ChatKarlo.service;

import com.ChatKarlo.ChatKarlo.dto.MemberFormDto;
import com.ChatKarlo.ChatKarlo.dto.PromptFormDto;
import com.ChatKarlo.ChatKarlo.entity.Member;
import com.ChatKarlo.ChatKarlo.repository.MemberRepository;
import com.ChatKarlo.ChatKarlo.repository.PromptRepository;
import com.intellijava.core.controller.RemoteImageModel;
import com.intellijava.core.controller.RemoteLanguageModel;
import com.intellijava.core.model.input.ImageModelInput;
import com.intellijava.core.model.input.LanguageModelInput;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class PromptServiceTest {
    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PromptRepository promptRepository;

    @Value("${openai.api.key}")
    String apiKey;


    @Test
    @DisplayName("ChatGPT Prompt 추출 테스트")
    @WithMockUser(username = "admin")
    void makeAfterwords() throws Exception{
        PromptFormDto promptFormDto = PromptFormDto.builder()
                .beforeWords("무지개색 고양이를 보고싶어")
                .build();

        String inputText = "Please change \""+ promptFormDto.getBeforeWords()
                +"\" for the Dall-E prompt by english and just give me only the prompt because i want to use the prompt to Dall-E API directly";

        RemoteLanguageModel languageModel = new RemoteLanguageModel(apiKey, "openai");
        LanguageModelInput langInput = new LanguageModelInput.Builder(inputText)
                .setModel("text-davinci-003").setTemperature(0.7f).setMaxTokens(50).build();
        String afterwords = languageModel.generateText(langInput);
        System.out.println(afterwords);
    }

    @Test
    @DisplayName("Dall-E 이미지 생성 테스트")
    void makeImage() throws Exception{
        String afterwords = "I want to see a rainbow-colored cat.";
        RemoteImageModel imageModel = new RemoteImageModel(apiKey, "openai");
        ImageModelInput imageInput = new ImageModelInput.Builder(afterwords)
                .setNumberOfImages(1).setImageSize("1024x1024").build();
        String URL = imageModel.generateImages(imageInput).get(0);
        System.out.println(URL);
    }
}