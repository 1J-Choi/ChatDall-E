package com.ChatKarlo.ChatKarlo.service;

import com.ChatKarlo.ChatKarlo.dto.PromptDto;
import com.ChatKarlo.ChatKarlo.dto.PromptFormDto;
import com.ChatKarlo.ChatKarlo.entity.Member;
import com.ChatKarlo.ChatKarlo.entity.Prompt;
import com.ChatKarlo.ChatKarlo.repository.MemberRepository;
import com.ChatKarlo.ChatKarlo.repository.PromptRepository;
import com.intellijava.core.controller.RemoteImageModel;
import com.intellijava.core.controller.RemoteLanguageModel;
import com.intellijava.core.model.input.ImageModelInput;
import com.intellijava.core.model.input.LanguageModelInput;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;

@Service
@Transactional(timeout = 1800000)
@RequiredArgsConstructor
public class PromptService {

    private final PromptRepository promptRepository;
    private final MemberRepository memberRepository;

    @Value("${openai.api.key}")
    private String apiKey;
    public PromptFormDto findPromptFormDto(Long nowMemberId){
        Prompt prompt = promptRepository.findByMember_Id(nowMemberId);
        PromptFormDto promptFormDto;
        if (prompt == null){
            Member member = memberRepository.getReferenceById(nowMemberId);
            prompt = Prompt.createPrompt(member);
            promptFormDto = PromptFormDto.builder()
                    .id(prompt.getId())
                    .build();
        } else{
            promptFormDto = PromptFormDto.builder()
                    .id(prompt.getId())
                    .beforeWords(prompt.getBeforeWords())
                    .url(prompt.getUrl())
                    .build();
        }

        return promptFormDto;
    }

    public Long savePrompt(PromptFormDto promptFormDto, String email) throws IOException {
        Member member = memberRepository.findByEmail(email);

        String inputText = "Please change \""+ promptFormDto.getBeforeWords()
                +"\" for the Dall-E prompt by english and just give me only the prompt because i want to use the prompt to Dall-E API directly";

        RemoteLanguageModel languageModel = new RemoteLanguageModel(apiKey, "openai");
        LanguageModelInput langInput = new LanguageModelInput.Builder(inputText)
                .setModel("text-davinci-003").setTemperature(0.7f).setMaxTokens(50).build();
        String afterwords = languageModel.generateText(langInput);

        RemoteImageModel imageModel = new RemoteImageModel(apiKey, "openai");
        ImageModelInput imageInput = new ImageModelInput.Builder(afterwords)
                .setNumberOfImages(1).setImageSize("1024x1024").build();
        String URL = imageModel.generateImages(imageInput).get(0);

        PromptDto promptDto = PromptDto.builder()
                .id(promptFormDto.getId())
                .beforeWords(promptFormDto.getBeforeWords())
                .afterWords(afterwords)
                .url(URL)
                .member(member)
                .build();

        Prompt prompt = promptDto.createPrompt();
        promptRepository.save(prompt);
        return prompt.getId();
    }

//    public Long savePromptTest(PromptFormDto promptFormDto, String email) throws IOException {
//        Member member = memberRepository.findByEmail(email);
//
//        String afterwords = promptFormDto.getBeforeWords()+"1";
//
//        String URL = "https://user-images.githubusercontent.com/13268420/112147492-1ab76200-8c20-11eb-8649-3d2f282c3c02.png";
//
//        PromptDto promptDto = PromptDto.builder()
//                .id(promptFormDto.getId())
//                .beforeWords(promptFormDto.getBeforeWords())
//                .afterWords(afterwords)
//                .url(URL)
//                .member(member)
//                .build();
//
//        Prompt prompt = promptDto.createPrompt();
//        promptRepository.save(prompt);
//        return prompt.getId();
//    }
}
